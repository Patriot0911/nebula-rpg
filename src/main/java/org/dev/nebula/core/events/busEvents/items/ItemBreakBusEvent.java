package org.dev.nebula.core.events.busEvents.items;

import org.bukkit.event.player.PlayerItemBreakEvent;

public class ItemBreakBusEvent {
    public final PlayerItemBreakEvent event;

    public ItemBreakBusEvent(PlayerItemBreakEvent event) {
        this.event = event;
    }
}
