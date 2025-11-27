package org.dev.nebula.core.eventBus;

import org.bukkit.inventory.ItemStack;

public abstract class ItemBusEventBase<T> {
    protected final ItemStack item;
    public final T event; // delete later

    public ItemBusEventBase(ItemStack item, T event) {
        this.item = item;
        this.event = event;
    };

    public ItemStack getItem() {
        return this.item;
    }
};
