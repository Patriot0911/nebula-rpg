package org.dev.nebula.core.items;

import org.dev.nebula.core.events.EventBus;
import org.dev.nebula.core.services.ItemsService;
import org.dev.nebula.core.services.UsersService;

public class ItemManager {
    public static final String PluginNameSpace = "nebula";
    public static final String WeaponNameSpace = PluginNameSpace+".weapons";

    private final EventBus eventBus;
    private final UsersService userService;
    private final ItemsService itemsService;

    public ItemManager(EventBus bus, UsersService userService, ItemsService itemsService) {
        this.eventBus = bus;
        this.userService = userService;
        this.itemsService = itemsService;
    }

    public void loadItems() {
        for (Class<? extends ItemBase> itemClass : itemsService.getItemsList()) {
            try {
                ItemBase item = itemClass
                    .getDeclaredConstructor(EventBus.class, UsersService.class)
                    .newInstance(eventBus, userService);
                itemsService.registerItem(item.getItemKeyName(), item);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Cannot load item: " + itemClass.getName());
            }
        }
    }
}
