package org.dev.nebula;

import org.bukkit.plugin.java.JavaPlugin;
import org.dev.nebula.core.bridge.CoreEventBridgeListener;
import org.dev.nebula.core.eventBus.EventBus;
import org.dev.nebula.core.eventBus.NebulaEventBus;
import org.dev.nebula.core.skills.SkillsManager;

public class NebulaPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        EventBus bus = new NebulaEventBus();

        getServer().getPluginManager().registerEvents(
                new CoreEventBridgeListener(bus), this
        );

        new SkillsManager(bus).registerPassiveSkills();
    }
}
