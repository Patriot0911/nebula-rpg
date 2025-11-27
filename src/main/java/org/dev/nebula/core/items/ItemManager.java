package org.dev.nebula.core.items;

import java.util.HashMap;
import java.util.Map;

import org.dev.nebula.core.eventBus.EventBus;
import org.dev.nebula.core.services.ItemsService;
import org.dev.nebula.core.services.UserService;

public class ItemManager {
    public static final String PluginNameSpace = "nebula";
    public static final String WeaponNameSpace = PluginNameSpace+".weapons";

    private final EventBus eventBus;
    private final UserService userService;
    private final ItemsService itemsService;

    private final Map<String, ItemBase> items = new HashMap<>();

    public ItemManager(EventBus bus, UserService userService, ItemsService itemsService) {
        this.eventBus = bus;
        this.userService = userService;
        this.itemsService = itemsService;
    }

    public void registerItems() {
        for (Class<? extends ItemBase> itemClass : itemsService.getItemList()) {
            try {
                ItemBase item = itemClass
                    .getDeclaredConstructor(EventBus.class, UserService.class)
                    .newInstance(eventBus, userService);
                items.put(item.getItemKeyName(), item);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Cannot load item: " + itemClass.getName());
            }
        }
    }
}
