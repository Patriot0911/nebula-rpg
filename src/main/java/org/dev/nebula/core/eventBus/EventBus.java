package org.dev.nebula.core.eventBus;

public interface EventBus {
    <T> void subscribe(Class<T> eventClass, EventListener<T> listener);
    <T> void publish(T event);
}
