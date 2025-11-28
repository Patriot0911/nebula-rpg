package org.dev.nebula.core.events.busEvents.items;

import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractBusEvent {
    public final PlayerInteractEvent event;

    public PlayerInteractBusEvent(PlayerInteractEvent event) {
        this.event = event;
    }
}
