package org.dev.nebula.core.items;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.dev.nebula.core.services.UserService;

public abstract class ItemBase {
    protected UserService userService;

    public ItemBase(UserService userService) {
        this.userService = userService;
    }

    public abstract ItemStack createItemStack();

    public abstract String getItemName();
    public abstract String getItemDescription();
    public abstract NamespacedKey getItemTag();

    public void onInteract() {}
    public void onDrop() {}
}
