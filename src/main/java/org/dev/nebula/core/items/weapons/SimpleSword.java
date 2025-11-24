package org.dev.nebula.core.items.weapons;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.dev.nebula.core.items.ItemBase;
import org.dev.nebula.core.services.UserService;

public class SimpleSword extends ItemBase {

    public SimpleSword(UserService userService) {
        super(userService);
    }

    @Override
    public ItemStack createItemStack() {
        throw new UnsupportedOperationException("Unimplemented method 'createItemStack'");
    }

    @Override
    public String getItemName() {
        throw new UnsupportedOperationException("Unimplemented method 'getItemName'");
    }

    @Override
    public String getItemDescription() {
        throw new UnsupportedOperationException("Unimplemented method 'getItemDescription'");
    }

    @Override
    public NamespacedKey getItemTag() {
        throw new UnsupportedOperationException("Unimplemented method 'getItemTag'");
    }
}
