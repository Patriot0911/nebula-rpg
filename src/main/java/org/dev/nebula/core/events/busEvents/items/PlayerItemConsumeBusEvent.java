package org.dev.nebula.core.events.busEvents.items;

import org.bukkit.event.player.PlayerItemConsumeEvent;

public class PlayerItemConsumeBusEvent {
    public final PlayerItemConsumeEvent event;

    public PlayerItemConsumeBusEvent(PlayerItemConsumeEvent event) {
        this.event = event;
    }
}
