package org.dev.nebula.core.items;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.dev.nebula.core.services.UserService;

public abstract class ItemBase {
    protected UserService userService;

    public ItemBase(UserService userService) {
        this.userService = userService;
    }

    public abstract ItemStack createItemStack();

    public abstract String getItemName();
    public abstract String getItemDescription();

    public abstract String getItemKeyName();
    public abstract NamespacedKey getItemTag();

    protected void setItemTagName(ItemMeta itemMeta) {
        itemMeta.getPersistentDataContainer().set(
            getItemTag(),
            PersistentDataType.BYTE,
            (byte) 1
        );
    }
    protected boolean isSameItem(ItemMeta itemMeta) {
        return itemMeta.getPersistentDataContainer().has(
            getItemTag(),
            PersistentDataType.BYTE
        );
    }
}
