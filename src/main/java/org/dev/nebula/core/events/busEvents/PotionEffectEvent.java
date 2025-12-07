package org.dev.nebula.core.events.busEvents;

import org.bukkit.event.entity.EntityPotionEffectEvent;

public class PotionEffectEvent {
    public final EntityPotionEffectEvent event;

    public PotionEffectEvent(EntityPotionEffectEvent event) {
        this.event = event;
    }
}
