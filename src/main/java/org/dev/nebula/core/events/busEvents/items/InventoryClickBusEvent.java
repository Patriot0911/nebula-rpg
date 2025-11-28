package org.dev.nebula.core.events.busEvents.items;

import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickBusEvent {
    public final InventoryClickEvent event;

    public InventoryClickBusEvent(InventoryClickEvent event) {
        this.event = event;
    }
}
