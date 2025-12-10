package org.dev.nebula.core.spawn.interfaces;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public abstract class CustomMob {
    public abstract EntityType getEntityType();

    public abstract void modifyEntity(LivingEntity entity);
}
