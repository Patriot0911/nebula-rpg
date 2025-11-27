package org.dev.nebula.core.services;

import java.util.ArrayList;
import java.util.List;

import org.dev.nebula.core.items.ItemBase;
import org.dev.nebula.core.items.weapons.SimpleSword;

public class ItemsService {
    private final List<Class<? extends ItemBase>> itemsList = new ArrayList<Class<? extends ItemBase>>(
        List.of(
            SimpleSword.class
        )
    );

    public ItemsService() {}

    public List<Class<? extends ItemBase>> getItemList() {
        return itemsList;
    }
}
