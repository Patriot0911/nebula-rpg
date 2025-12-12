package org.dev.nebula.core.items;

import java.util.List;
import java.util.Map;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.dev.nebula.core.crafts.CraftCondition;
import org.dev.nebula.core.events.EventBus;

public abstract class ItemBase {
    public ItemBase(EventBus bus) {}

    public static final NamespacedKey ITEM_ID_KEY_NAMESPACED_KEY =  new NamespacedKey("nebula", "item_id");

    public abstract ItemStack createItemStack(Integer count);

    public abstract String getItemName();
    public abstract String getItemDescription();

    public abstract String getItemKeyName();

    protected void setItemTagName(ItemMeta itemMeta) {
        itemMeta.getPersistentDataContainer().set(
            ITEM_ID_KEY_NAMESPACED_KEY,
            PersistentDataType.STRING,
            getItemKeyName()
        );
    }

    protected boolean isSameItem(ItemMeta itemMeta) {
        String key = itemMeta.getPersistentDataContainer().get(
            ITEM_ID_KEY_NAMESPACED_KEY,
            PersistentDataType.STRING
        );
        return key == getItemKeyName();
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
        return new CraftCondition[] {};
    }
}
