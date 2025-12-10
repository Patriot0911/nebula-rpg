package org.dev.nebula.core.events.buses;

import org.bukkit.entity.Player;

public abstract class PlayerBusEventBase<T> {
    protected final Player producer;
    public final T event;

    public PlayerBusEventBase(Player producer, T event) {
        this.producer = producer;
        this.event = event;
    };

    public Player getProducer() {
        return producer;
    }
};
