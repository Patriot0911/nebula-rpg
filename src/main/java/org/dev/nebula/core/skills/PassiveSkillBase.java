package org.dev.nebula.core.skills;

import org.dev.nebula.core.eventBus.EventBus;
import org.dev.nebula.core.services.UserService;

public abstract class PassiveSkillBase {
    protected final UserService userService;

    public PassiveSkillBase(EventBus bus, UserService userService) {
        this.userService = userService;
    }
}
