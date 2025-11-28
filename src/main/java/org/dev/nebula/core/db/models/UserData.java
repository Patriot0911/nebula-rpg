package org.dev.nebula.core.db.models;

import java.util.HashMap;
import java.util.UUID;

public class UserData {

    private final UUID id;
    private final String nickName;
    private int level;
    private int exp;
    private HashMap<String, SkillData> skills = new HashMap<String, SkillData>();

    public UserData(UUID id, String nickName, int level, int exp) {
        this.id = id;
        this.nickName = nickName;
        this.level = level;
        this.exp = exp;
    }

    public UUID getId() {
        return id;
    }
    public String getNickName() {
        return nickName;
    }
    public int getLevel() {
        return level;
    }
    public int getExp() {
        return exp;
    }

    // Note: For force loading skills from DB
    public void loadSkill(SkillData skillData) {
        skills.put(skillData.getName(), skillData);
    }
    public void addSkill(SkillData skillData) {
        SkillData existingSkill = skills.get(skillData.getName());
        if(existingSkill != null) return;
        if(hasSkill(skillData.getName())) {
            skillData.setModified(true);
        } else {
            skillData.setNew(true);
        }
        skills.put(skillData.getName(), skillData);
    }
    public void removeSkill(String skillName) {
        SkillData skillData = skills.get(skillName);
        if(skillData == null) return;
        if(skillData.isNew()) {
            skills.remove(skillName);
            return;
        }
        skills.put(skillName, null);
    }
    public SkillData getSkill(String skillName) {
        return skills.get(skillName);
    }
    public boolean hasSkill(String skillName) {
        return skills.containsKey(skillName);
    }
    public HashMap<String, SkillData> getAllSkills() {
        return this.skills;
    }

    public int getLevelUpExp() {
        return (level + 1) * 75;
    }
    public void addExp(int amount) {
        exp += amount;
        while (exp >= getLevelUpExp()) {
            exp -= getLevelUpExp();
            level++;
        }
    }
}
