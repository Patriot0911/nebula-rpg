package org.dev.nebula.core.spawn;

import org.bukkit.event.Listener;
import org.dev.nebula.core.services.MobsService;
import org.dev.nebula.core.spawn.interfaces.MobDropList;

public class SpawnManager implements Listener {
    protected MobsService mobsService;

    public SpawnManager(MobsService mobsService) {
        this.mobsService = mobsService;
    }

    public void registerDropList() {
        for (Class<? extends MobDropList> mobDropClass : mobsService.getMobDropsList()) {
            try {
                MobDropList mobDrop = mobDropClass
                    .getDeclaredConstructor()
                    .newInstance();
            mobsService.registerMobDrop(mobDrop.getMobKey(), mobDrop);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Cannot load mobDrop: " + mobDropClass.getName());
            }
        }
    }
}
