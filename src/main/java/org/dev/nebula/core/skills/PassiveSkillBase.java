package org.dev.nebula.core.skills;

import org.dev.nebula.core.db.models.UserData;
import org.dev.nebula.core.eventBus.EventBus;
import org.dev.nebula.core.eventBus.PlayerBusEventBase;
import org.dev.nebula.core.eventBus.PlayerEventListener;
import org.dev.nebula.core.services.UserService;

public abstract class PassiveSkillBase {
    protected final UserService userService;
    private final EventBus eventBus;

    public PassiveSkillBase(EventBus bus, UserService userService) {
        this.userService = userService;
        this.eventBus = bus;
    }

    @SuppressWarnings("rawtypes")
    public <T extends PlayerBusEventBase> void subscribeSkillEvent(Class<T> eventClass, PlayerEventListener<T> listener, String skillName) {
        eventBus.subscribe(eventClass,
            event -> {
                UserData userData = userService.getUserData(event.getProducer().getUniqueId());
                if(userData == null || !userData.hasSkill(skillName)) return;
                listener.handle(event, userData);
            }
        );
    };
}
