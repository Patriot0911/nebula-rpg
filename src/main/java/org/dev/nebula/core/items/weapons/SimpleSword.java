package org.dev.nebula.core.items.weapons;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.dev.nebula.core.eventBus.EventBus;
import org.dev.nebula.core.items.ItemBase;
import org.dev.nebula.core.services.UserService;

public class SimpleSword extends ItemBase {
    public static final String ITEM_NAME = "simple_sword";

    public SimpleSword(EventBus bus, UserService userService) {
        super(bus, userService);
    }

    @Override
    public ItemStack createItemStack() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + getItemName());
        meta.setLore(List.of(getItemDescription()));

        meta.addItemFlags(
            ItemFlag.HIDE_ATTRIBUTES,
            ItemFlag.HIDE_UNBREAKABLE,
            ItemFlag.HIDE_ENCHANTS
        );

        meta.getPersistentDataContainer().set(
            getItemTag(),
            PersistentDataType.STRING,
            ITEM_NAME
        );

        item.setItemMeta(meta);
        return item;
    }

    @Override
    public NamespacedKey getItemTag() {
        return new NamespacedKey("nebula.weapons", ITEM_NAME);
    }
    @Override
    public String getItemKeyName() {
        return SimpleSword.ITEM_NAME;
    }

    @Override
    public String getItemName() {
        return "Test Name";
    }
    @Override
    public String getItemDescription() {
        return "Test Description";
    }
}
