package org.dev.nebula.core.events.bases;

import org.bukkit.event.Event;

public abstract class BusEventBase<T extends Event> {
    public final T event;

    public BusEventBase(T event) {
        this.event = event;
    }
}
