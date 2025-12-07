package org.dev.nebula.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dev.nebula.core.items.ItemBase;
import org.dev.nebula.core.items.weapons.SimpleSword;

public class ItemsService {
    private final List<Class<? extends ItemBase>> itemsList = new ArrayList<Class<? extends ItemBase>>(
        List.of(
            SimpleSword.class
        )
    );
    private final Map<String, ItemBase> items = new HashMap<>();

    public ItemsService() {}

    public List<Class<? extends ItemBase>> getItemsList() {
        return itemsList;
    }
    public void registerItem(String key, ItemBase item) {
        items.put(key, item);
    }
    public Map<String, ItemBase> getItems() {
        return items;
    }
}
