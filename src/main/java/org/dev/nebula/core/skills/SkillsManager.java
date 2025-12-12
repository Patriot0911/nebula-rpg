package org.dev.nebula.core.skills;

import org.dev.nebula.core.events.EventBus;
import org.dev.nebula.core.services.SkillsService;

public class SkillsManager {
    private final EventBus eventBus;
    private final SkillsService skillsService;

    public SkillsManager(EventBus bus, SkillsService skillsService) {
        this.eventBus = bus;
        this.skillsService = skillsService;
    }

    public void registerPassiveSkills() {
        for (Class<? extends PassiveSkillBase> skillClass : skillsService.getPassiveSkills().values()) {
            try {
                skillClass
                    .getDeclaredConstructor(EventBus.class)
                    .newInstance(eventBus);

            } catch (Exception e) {
                throw new RuntimeException("Cannot initialize passive skill: " + skillClass.getName(), e);
            }
        }
    }
}
