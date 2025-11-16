package org.dev.nebula.core.skills;

import org.dev.nebula.core.eventBus.EventBus;
import org.dev.nebula.core.skills.passive.LifeStealPassive;

public class SkillsManager {

    private final EventBus eventBus;

    public SkillsManager(EventBus bus) {
        this.eventBus = bus;
    }

    public void registerPassiveSkills() {
        new LifeStealPassive(this.eventBus);
    }
}
