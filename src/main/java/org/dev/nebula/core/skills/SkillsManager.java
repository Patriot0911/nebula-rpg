package org.dev.nebula.core.skills;

import org.dev.nebula.core.events.EventBus;
import org.dev.nebula.core.services.SkillsService;
import org.dev.nebula.core.services.UserService;

public class SkillsManager {

    private final EventBus eventBus;
    private final UserService userService;
    private final SkillsService skillsService;

    public SkillsManager(EventBus bus, UserService userService, SkillsService skillsService) {
        this.eventBus = bus;
        this.userService = userService;
        this.skillsService = skillsService;
    }

    public void registerPassiveSkills() {
        for (Class<? extends PassiveSkillBase> skillClass : skillsService.getPassiveSkills().values()) {
            try {
                skillClass
                    .getDeclaredConstructor(EventBus.class, UserService.class)
                    .newInstance(eventBus, userService);

            } catch (Exception e) {
                throw new RuntimeException("Cannot initialize passive skill: " + skillClass.getName(), e);
            }
        }
    }
}
