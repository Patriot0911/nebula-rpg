package org.dev.nebula.core.skills;

import org.dev.nebula.core.eventBus.EventBus;
import org.dev.nebula.core.services.UserService;
import org.dev.nebula.core.skills.passive.LifeStealPassive;

public class SkillsManager {

    private final EventBus eventBus;
    private final UserService userService;

    public SkillsManager(EventBus bus, UserService userService) {
        this.eventBus = bus;
        this.userService = userService;
    }

    public void registerPassiveSkills() {
        new LifeStealPassive(this.eventBus, userService);
    }
}
