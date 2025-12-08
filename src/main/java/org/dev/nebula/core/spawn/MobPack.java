package org.dev.nebula.core.spawn;

import java.util.ArrayList;
import java.util.List;

import org.dev.nebula.core.spawn.interfaces.CustomMob;
import org.dev.nebula.core.spawn.interfaces.SpawnRule;

public abstract class MobPack {
    private final List<CustomMob> mobs;
    private final int minAmount;
    private final int maxAmount;

    public MobPack(List<CustomMob> mobs, int min, int max) {
        this.mobs = mobs;
        this.minAmount = min;
        this.maxAmount = max;
    }

    public List<CustomMob> roll() {
        int amount = minAmount + (int)(Math.random() * (maxAmount - minAmount + 1));
        List<CustomMob> result = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            CustomMob mob = mobs.get((int) (Math.random() * mobs.size()));
            result.add(mob);
        }
        return result;
    }

    public SpawnDefinition toSpawnDefinition() {
        return new SpawnDefinition(
            getPackId(),
            getSpawnRules(),
            this,
            getSpawnChance(),
            getSpawnRadius(),
            getSpawnLimit(),
            getSpawnCooldown(),
            getSpawnLocCooldown()
        );
    }

    public abstract List<SpawnRule> getSpawnRules();
    public abstract String getPackId();
    public abstract double getSpawnChance();
    public abstract int getSpawnRadius();
    public abstract int getSpawnLimit();
    public abstract long getSpawnCooldown();
    public abstract long getSpawnLocCooldown();
}

