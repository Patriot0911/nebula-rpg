package org.dev.nebula.core.menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Consumer;

public class MenuBuilder {
    private MenuMode mode = MenuMode.GUI;
    private final Set<Integer> blockedSlots = new HashSet<>();

    private String title = "";
    private int size = 9;
    private final Map<Integer, MenuItem> items = new HashMap<>();
    private ItemStack filler;

    private int displayStartX = -1;
    private int displayStartY = -1;
    private int displayEndX = -1;
    private int displayEndY = -1;

    private List<ItemStack> paginatedItems = new ArrayList<>();
    private int itemsPerPage = 0;
    private int page = 0;

    private Consumer<InventoryClickEvent> filterHandler = null;
    private Consumer<InventoryClickEvent> paginatedHandler = null;

    private final Map<Integer, Consumer<InventoryClickEvent>> handlers = new HashMap<>();

    public static MenuBuilder create() {
        return new MenuBuilder();
    }

    public MenuBuilder title(String title) {
        this.title = title;
        return this;
    }

    public MenuBuilder size(int rows) {
        this.size = rows * 9;
        return this;
    }

    public MenuBuilder filler(ItemStack item) {
        this.filler = item;
        return this;
    }

    public MenuBuilder setItem(int slot, ItemStack item, Consumer<InventoryClickEvent> click) {
        items.put(slot, new MenuItem(item, click));
        return this;
    }

    public MenuBuilder setFilterHandler(Consumer<InventoryClickEvent> click) {
        this.filterHandler = click;
        return this;
    }

    public MenuBuilder setPaginatedHandler(Consumer<InventoryClickEvent> click) {
        this.paginatedHandler = click;
        return this;
    }

    public MenuBuilder displayArea(int startX, int stratY, int endX, int endY) {
        this.displayStartX = startX;
        this.displayStartY = stratY;
        this.displayEndX = endX;
        this.displayEndY = endY;
        this.itemsPerPage = ((endX - startX) * (endY - stratY)) + 1;
        return this;
    }

    public MenuBuilder paginated(List<ItemStack> list) {
        this.paginatedItems = list;
        return this;
    }

    public MenuBuilder page(int page) {
        this.page = page;
        return this;
    }

    private void fillPagination() {
        if (displayStartX == -1 || displayStartY == -1) return;

        int startIndex = page * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, paginatedItems.size());

        int invSlot = displayStartX + (displayStartY*9);
        int itemsPerRow = displayEndX - displayStartX;

        for (int i = startIndex, itemIndex = 0; i < endIndex; i++, itemIndex++) {
            ItemStack item = paginatedItems.get(i);
            items.put(invSlot, new MenuItem(item, this.paginatedHandler));
            invSlot = displayStartX + (itemIndex % 9) + (displayStartY*itemsPerRow + Math.ceilDiv(itemIndex, itemsPerRow));
        }
    }

    @SuppressWarnings("deprecation")
    public Inventory build(Player player) {
        Inventory inv = Bukkit.createInventory(null, size, title);

        if (filler != null) {
            for (int i = 0; i < size; i++) {
                int row = Math.ceilDiv(i, 9);
                int col = i % 9;
                if (
                    (row < displayStartY || row > displayEndY) ||
                    (col < displayStartX || col > displayEndX)
                ) continue;
                inv.setItem(i, filler);
            }
        }

        fillPagination();

        for (Map.Entry<Integer, MenuItem> e : items.entrySet()) {
            inv.setItem(e.getKey(), e.getValue().item());
            if (e.getValue().handler() != null) {
                handlers.put(e.getKey(), e.getValue().handler());
            }
        }

        MenuRegistry.register(player, this);

        return inv;
    }

    public void handleClick(InventoryClickEvent e) {
        if (mode == MenuMode.STORAGE) {
            return;
        }

        if (mode == MenuMode.GUI) {
            e.setCancelled(true);
            Consumer<InventoryClickEvent> handler = handlers.get(e.getSlot());
            if (handler != null) handler.accept(e);
            return;
        }

        if (mode == MenuMode.HYBRID) {
            // todo with filters
            if (blockedSlots.contains(e.getSlot())) {
                e.setCancelled(true);
                Consumer<InventoryClickEvent> handler = handlers.get(e.getSlot());
                if (handler != null) handler.accept(e);
            } else {
                return;
            }
        }
    }

    public MenuBuilder mode(MenuMode mode) {
        this.mode = mode;
        return this;
    }

    public MenuBuilder blockSlot(int slot) {
        this.blockedSlots.add(slot);
        return this;
    }

    private record MenuItem(ItemStack item, Consumer<InventoryClickEvent> handler) {}
}
