package org.dev.nebula.core.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NebulaEventBus implements EventBus {

    private final Map<Class<?>, List<EventListener<?>>> listeners = new HashMap<>();

    @Override
    public synchronized <T> void subscribe(Class<T> eventClass, EventListener<T> listener) {
        listeners
                .computeIfAbsent(eventClass, c -> new ArrayList<>())
                .add(listener);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> void publish(T event) {
        listeners.getOrDefault(event.getClass(), List.of())
                .forEach(l -> ((EventListener<T>) l).handle(event));
    }
}
