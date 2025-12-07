package org.dev.nebula.core.skills;

import org.dev.nebula.core.events.EventBus;
import org.dev.nebula.core.services.SkillsService;
import org.dev.nebula.core.services.UsersService;

public class SkillsManager {

    private final EventBus eventBus;
    private final UsersService userService;
    private final SkillsService skillsService;

    public SkillsManager(EventBus bus, UsersService userService, SkillsService skillsService) {
        this.eventBus = bus;
        this.userService = userService;
        this.skillsService = skillsService;
    }

    public void registerPassiveSkills() {
        for (Class<? extends PassiveSkillBase> skillClass : skillsService.getPassiveSkills().values()) {
            try {
                skillClass
                    .getDeclaredConstructor(EventBus.class, UsersService.class)
                    .newInstance(eventBus, userService);

            } catch (Exception e) {
                throw new RuntimeException("Cannot initialize passive skill: " + skillClass.getName(), e);
            }
        }
    }
}
