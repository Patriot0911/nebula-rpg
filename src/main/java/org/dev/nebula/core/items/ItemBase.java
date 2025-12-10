package org.dev.nebula.core.items;

import java.util.List;
import java.util.Map;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.dev.nebula.core.crafts.CraftCondition;
import org.dev.nebula.core.services.UsersService;

public abstract class ItemBase {
    protected UsersService userService;

    public ItemBase(UsersService userService) {
        this.userService = userService;
    }

    public abstract ItemStack createItemStack(Integer count);

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

    public String[] getCraftShape() {
        return null;
    }
    public List<ItemStack> getCraftShapeLess() {
        return null;
    }
    public Map<Character, ItemStack> getCraftMapping() {
        return null;
    }
    public CraftCondition[] getCraftConditions() {
        return null;
    }
}
