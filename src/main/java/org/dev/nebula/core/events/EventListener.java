package org.dev.nebula.core.events;

@FunctionalInterface
public interface EventListener<T> {
    void handle(T event);
}

