package org.dev.nebula.core.eventBus;

import org.dev.nebula.core.db.models.UserData;

@FunctionalInterface
public interface PlayerEventListener<T> {
    void handle(T event, UserData userData);
}

