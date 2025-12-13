package org.dev.nebula.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dev.nebula.core.items.ItemBase;
import org.dev.nebula.core.items.armor.IceBoots;
import org.dev.nebula.core.items.armor.IceChestplate;
import org.dev.nebula.core.items.armor.IceHelmet;
import org.dev.nebula.core.items.armor.IceLeggings;
import org.dev.nebula.core.items.etc.AchievementsBook;
import org.dev.nebula.core.items.shards.IceCrystal;
import org.dev.nebula.core.items.shards.IceShard;
import org.dev.nebula.core.items.weapons.IceSword;

public class ItemsService {
    private final List<Class<? extends ItemBase>> itemsList = new ArrayList<Class<? extends ItemBase>>(
        List.of(
            IceSword.class,
            IceShard.class,
            IceBoots.class,
            IceLeggings.class,
            IceChestplate.class,
            IceHelmet.class,
            AchievementsBook.class,
            IceCrystal.class
        )
    );
    private static final Map<String, ItemBase> items = new HashMap<>();

    public ItemsService() {}

    public List<Class<? extends ItemBase>> getItemsList() {
        return itemsList;
    }
    public void registerItem(String key, ItemBase item) {
        items.put(key, item);
    }
    public static Map<String, ItemBase> getItems() {
        return items;
    }
    public static ItemBase getItem(String key) {
        return items.get(key);
    }
}
