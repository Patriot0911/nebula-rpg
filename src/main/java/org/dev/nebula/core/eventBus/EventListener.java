package org.dev.nebula.core.eventBus;

@FunctionalInterface
public interface EventListener<T> {
    void handle(T event);
}

