package org.dev.nebula.core.spawn;

import org.bukkit.event.Listener;
import org.dev.nebula.core.services.ItemsService;
import org.dev.nebula.core.services.MobsService;
import org.dev.nebula.core.services.UsersService;
import org.dev.nebula.core.spawn.interfaces.MobDropList;

public class SpawnManager implements Listener {
    protected ItemsService itemsService;
    protected UsersService userService;
    protected MobsService mobsService;

    public SpawnManager(MobsService mobsService, ItemsService itemsService, UsersService userService) {
        this.mobsService = mobsService;
        this.itemsService = itemsService;
        this.userService = userService;
    }

    public void registerDropList() {
        for (Class<? extends MobDropList> mobDropClass : mobsService.getMobDropsList()) {
            try {
                MobDropList mobDrop = mobDropClass
                    .getDeclaredConstructor(UsersService.class, ItemsService.class)
                    .newInstance(userService, itemsService);
            mobsService.registerMobDrop(mobDrop.getMobKey(), mobDrop);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Cannot load mobDrop: " + mobDropClass.getName());
            }
        }
    }
}
