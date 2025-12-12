package org.dev.nebula.core.skills;

import org.dev.nebula.core.db.models.UserData;
import org.dev.nebula.core.events.EventBus;
import org.dev.nebula.core.events.PlayerEventListener;
import org.dev.nebula.core.events.buses.PlayerBusEventBase;
import org.dev.nebula.core.services.UsersService;

public abstract class PassiveSkillBase {
    private final EventBus eventBus;

    public PassiveSkillBase(EventBus bus) {
        this.eventBus = bus;
    }

    @SuppressWarnings("rawtypes")
    public <T extends PlayerBusEventBase> void subscribeSkillEvent(Class<T> eventClass, PlayerEventListener<T> listener, String skillName) {
        eventBus.subscribe(eventClass,
            event -> {
                UserData userData = UsersService.getUserData(event.getProducer().getUniqueId());
                if(userData == null || !userData.hasSkill(skillName)) return;
                listener.handle(event, userData);
            }
        );
    };
}
