package org.dev.nebula.core.events.busEvents.items;

import org.bukkit.event.entity.EntityPickupItemEvent;

public class EntityPickupItemBusEvent {
    public final EntityPickupItemEvent event;

    public EntityPickupItemBusEvent(EntityPickupItemEvent event) {
        this.event = event;
    }
}
