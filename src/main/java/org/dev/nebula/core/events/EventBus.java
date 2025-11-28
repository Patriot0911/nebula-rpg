package org.dev.nebula.core.events;

public interface EventBus {
    <T> void subscribe(Class<T> eventClass, EventListener<T> listener);
    <T> void publish(T event);
}
