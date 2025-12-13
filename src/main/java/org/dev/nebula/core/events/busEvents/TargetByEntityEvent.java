package org.dev.nebula.core.events.busEvents;

import org.bukkit.event.entity.EntityTargetEvent;

public class TargetByEntityEvent {
    public final EntityTargetEvent event;

    public TargetByEntityEvent(EntityTargetEvent event) {
        this.event = event;
    }
}
