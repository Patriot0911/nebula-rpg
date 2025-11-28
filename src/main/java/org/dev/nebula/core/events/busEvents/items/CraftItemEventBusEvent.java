package org.dev.nebula.core.events.busEvents.items;

import org.bukkit.event.inventory.CraftItemEvent;

public class CraftItemEventBusEvent {
    public final CraftItemEvent event;

    public CraftItemEventBusEvent(CraftItemEvent event) {
        this.event = event;
    }
}
