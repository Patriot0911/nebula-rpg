package org.dev.nebula.core.menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Consumer;

public class MenuBuilder {
    private final Set<Integer> blockedSlots = new HashSet<>();

    private String title = "";
    private int size = 9;
    private final Map<Integer, MenuItem> items = new HashMap<>();

    private final Map<Integer, Consumer<InventoryClickEvent>> handlers = new HashMap<>();

    private record MenuItem(ItemStack item, Consumer<InventoryClickEvent> handler) {}

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

    public MenuBuilder setItem(int slot, ItemStack item, Consumer<InventoryClickEvent> click) {
        items.put(slot, new MenuItem(item, click));
        return this;
    }

    public MenuBuilder setItem(
        int x, int y,
        ItemStack item,
        Consumer<InventoryClickEvent> click
    ) {
        int slot = y * 9 + x;
        items.put(slot, new MenuItem(item, click));
        return this;
    }

    public MenuBuilder fillItems(
        int startX,
        int startY,
        int endX,
        int endY,
        ItemStack item,
        Consumer<InventoryClickEvent> click
    ) {
        int width = endX - startX + 1;
        int height = endY - startY + 1;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int slot = (startY + y) * 9 + (startX + x);

                items.put(slot, new MenuItem(item, click));
            }
        }

        return this;
    }

    public MenuBuilder fillItems(
        int startX,
        int startY,
        int endX,
        int endY,
        ItemStack[][] items,
        Consumer<InventoryClickEvent> click
    ) {
        int width = endX - startX + 1;
        int height = endY - startY + 1;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int slot = (startY + y) * 9 + (startX + x);

                this.items.put(
                    slot,
                    new MenuItem(items[y][x], click)
                );
            }
        }

        return this;
    }

    @SuppressWarnings("deprecation")
    public Inventory build(Player player) {
        Inventory inv = Bukkit.createInventory(null, size, title);

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
        int raw = e.getRawSlot();
        Inventory inv = e.getInventory();

        System.out.println(raw);
        System.out.println(e.getSlot());

        if (raw < inv.getSize()) {
            if (blockedSlots.contains(raw)) {
                e.setCancelled(true);
            }
            Consumer<InventoryClickEvent> handler = handlers.get(raw);
            if (handler != null) {
                handler.accept(e);
            }
            return;
        }
    }

    public MenuBuilder blockSlot(int slot) {
        this.blockedSlots.add(slot);
        return this;
    }

    public MenuBuilder unBlockSlot(int slot) {
        this.blockedSlots.remove(slot);
        return this;
    }
}
