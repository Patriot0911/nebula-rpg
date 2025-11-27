package org.dev.nebula.core.eventBus.events.items;

import org.bukkit.event.player.PlayerInteractEvent;
import org.dev.nebula.core.eventBus.ItemBusEventBase;

public class PlayerInteract extends ItemBusEventBase<PlayerInteractEvent> {
    public PlayerInteract(PlayerInteractEvent event) {
        super(event.getItem(), event);
    }
}
