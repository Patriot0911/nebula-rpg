package org.dev.nebula;

import org.bukkit.plugin.java.JavaPlugin;
import org.dev.nebula.core.bridge.CoreEventBridgeListener;
import org.dev.nebula.core.eventBus.EventBus;
import org.dev.nebula.core.eventBus.NebulaEventBus;
import org.dev.nebula.core.mobSpawn.CustomMobRegistry;
import org.dev.nebula.core.mobSpawn.CustomMobSpawnListener;
import org.dev.nebula.core.mobSpawn.mobs.FrostZombie;
import org.dev.nebula.core.skills.SkillsManager;

public class NebulaPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        EventBus bus = new NebulaEventBus();

        getServer().getPluginManager().registerEvents(
                new CoreEventBridgeListener(bus), this
        );
        getServer().getPluginManager().registerEvents(
                new CustomMobSpawnListener(), this
        );

        registerCustomMobs();

        new SkillsManager(bus).registerPassiveSkills();
    }

    public void registerCustomMobs() {
        CustomMobRegistry.register(
            new FrostZombie()
        );
    }
}
