package org.dev.nebula.core.items.etc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.dev.nebula.core.db.models.AchievementUserData;
import org.dev.nebula.core.db.models.UserData;
import org.dev.nebula.core.events.EventBus;
import org.dev.nebula.core.events.busEvents.items.PlayerInteractBusEvent;
import org.dev.nebula.core.items.ItemBase;
import org.dev.nebula.core.menus.MenuBuilder;
import org.dev.nebula.core.services.UsersService;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class AchievementsBook extends ItemBase {
    public static final String ITEM_NAME = "achievements_book";

    public AchievementsBook(EventBus bus) {
        super(bus);
        bus.subscribe(PlayerInteractBusEvent.class, this::onPlayerInteract);
    }

    @Override
    public String getItemName() {
        return "Achievements Book";
    }
    @Override
    public String getItemDescription() {
        return null;
    }

    @Override
    public String getItemKeyName() {
        return AchievementsBook.ITEM_NAME;
    }


    @Override
    public String[] getCraftShape() {
        return new String[] {
            " AB",
            "   ",
            "   "
        };
    }
    @Override
    public Map<Character, ItemStack> getCraftMapping() {
        return Map.of(
            'B', new ItemStack(Material.BOOK),
            'A', new ItemStack(Material.AMETHYST_SHARD)
        );
    }

    @Override
    public ItemStack createItemStack(Integer count) {
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK, count == null ? 1 : count);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(
            Component
                .text(getItemName())
                .color(TextColor.color(225, 100, 225))
        );

        setItemTagName(meta);

        item.setItemMeta(meta);
        return item;
    }

    public void onPlayerInteract(PlayerInteractBusEvent e) {
        if (e.event.getHand() != EquipmentSlot.HAND) return;
        ItemStack itemStack = e.event.getPlayer().getInventory().getItemInMainHand();
        if (itemStack == null || itemStack.getType().isAir()) return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        System.out.println("Test before meta");
        if (itemMeta == null) return;
        if (!isSameItem(itemMeta)) return;
        e.event.setCancelled(true);
        Player player = e.event.getPlayer();

        MenuBuilder builder = createAchievementsMenu(0, player.getUniqueId());

        System.out.println("Test");

        player.openInventory(builder.build(player));
    }

    private MenuBuilder createAchievementsMenu(int page, UUID userId) {
        UserData userData = UsersService.getUserData(userId);

        MenuBuilder builder = MenuBuilder.create()
            .title("Achievements")
            .size(3);

        int pageSize = 18;

        List<Map.Entry<String, AchievementUserData>> list =
                new ArrayList<>(userData.getAchievements().entrySet());

        int from = page * pageSize;
        int to = Math.min(from + pageSize, list.size());

        List<Map.Entry<String, AchievementUserData>> pageItems = list.subList(from, to);

        for (int i = 0; i < pageItems.size(); i++) {
            var pageItem = pageItems.get(i);
            ItemStack achItem = new ItemStack(Material.BOOK);
            ItemMeta meta = achItem.getItemMeta();
            meta.displayName(
                Component
                    .text(pageItem.getKey())
                    .color(TextColor.color(200, 50, 255))
            );
            meta.lore(
                List.of(
                    Component
                        .text("Progress: " + pageItem.getValue().getProgress() + "/" + "todo"),
                    Component
                        .text("Test")
                        .color(TextColor.color(15, 200, 255))
                )
            );
            achItem.setItemMeta(meta);
            builder.blockSlot(i);
            builder.setItem(
                i, achItem,
                menuEvent -> {
                    System.out.print("TEST");
                }
            );
        }

        return builder;
    }
}
