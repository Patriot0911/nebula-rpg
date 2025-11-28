package org.dev.nebula.core.events;

import org.dev.nebula.core.db.models.UserData;

@FunctionalInterface
public interface PlayerEventListener<T> {
    void handle(T event, UserData userData);
}

