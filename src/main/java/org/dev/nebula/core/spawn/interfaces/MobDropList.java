package org.dev.nebula.core.spawn.interfaces;

import java.util.List;
import java.util.UUID;

import org.bukkit.event.entity.EntityDeathEvent;

public abstract class MobDropList {

    public boolean getPreventDefault() {
        return false;
    }

    public abstract List<MobDrop> getDropList();
    public abstract String getMobKey();
    public Integer getDropXp(EntityDeathEvent event) {
        return null;
    };
    public void operateUser(UUID userId) {};
}
