package org.dev.nebula.core.events.busEvents.items;

import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemBusEvent {
    public final PlayerDropItemEvent event;

    public PlayerDropItemBusEvent(PlayerDropItemEvent event) {
        this.event = event;
    }
}
