package org.dev.nebula.core.services;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.dev.nebula.core.db.dao.SkillDao;
import org.dev.nebula.core.skills.PassiveSkillBase;
import org.dev.nebula.core.skills.passive.IceLandAuthorityPasive;
import org.dev.nebula.core.skills.passive.LifeStealPassive;
import org.dev.nebula.core.skills.passive.RottenFoodPasive;

public class SkillsService {
    private final Map<String, Class<? extends PassiveSkillBase>> passiveSkills = new HashMap<>(
        Map.of(
            LifeStealPassive.SKILL_NAME, LifeStealPassive.class,
            RottenFoodPasive.SKILL_NAME, RottenFoodPasive.class,
            IceLandAuthorityPasive.SKILL_NAME, IceLandAuthorityPasive.class
        )
    );

    private final SkillDao skillDao;

    public SkillsService(SkillDao skillDao) {
        this.skillDao = skillDao;
    }

    public Map<String, Class<? extends PassiveSkillBase>> getPassiveSkills() {
        return passiveSkills;
    }
    public void loadAllSkills() {
        try {
            skillDao.syncSkills(
                passiveSkills.keySet()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
