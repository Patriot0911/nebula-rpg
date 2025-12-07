package org.dev.nebula.core.db.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UserData {

    private final UUID id;
    private final String nickName;
    private int level;
    private int exp;
    private HashMap<String, SkillData> skills = new HashMap<String, SkillData>();
    private HashMap<String, AchievementUserData> achievements = new HashMap<String, AchievementUserData>();

    private record AchievementUserData(String key, boolean isNew, boolean isDeleted) {
    }

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

    public void addAchievement(String achievement) {
        AchievementUserData data = new AchievementUserData(achievement, true, false);
        this.achievements.put(achievement, data);
    }
    public void removeAchievement(String achievement) {
        var data = this.achievements.get(achievement);
        if (data == null) return;
        if (data.isNew) {
            this.achievements.remove(data.key);
        } else {
            data = new AchievementUserData(achievement, false, true);
        };
        this.achievements.put(achievement, data);
    }
    public HashMap<String, AchievementUserData> getAchievements() {
        return this.achievements;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserData {")
            .append("id=").append(id).append(", ")
            .append("nickName='").append(nickName).append("', ")
            .append("level=").append(level).append(", ")
            .append("exp=").append(exp).append(", ")
            .append("skills=[");

        boolean first = true;
        for (SkillData skill : skills.values()) {
            if (skill == null) continue;

            if (!first) sb.append(", ");
            first = false;

            sb.append("{name=").append(skill.getName())
                .append(", level=").append(skill.getLevel())
                .append(", isNew=").append(skill.isNew())
                .append(", isModified=").append(skill.isModified())
                .append("}");
        }

        sb.append("]}");
        return sb.toString();
    }
}
