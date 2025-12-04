package org.dev.nebula.core.spawn;

import java.util.List;

import org.dev.nebula.core.spawn.interfaces.SpawnRule;

public class SpawnDefinition {
    private final String id;
    private final List<SpawnRule> rules;
    private final MobPack pack;
    private final double chance;

    private final int radius;
    private final int limit;
    private final long cooldownMs;
    private final long locationCooldownMs;

    public SpawnDefinition(
        String id,
        List<SpawnRule> rules,
        MobPack pack,
        double chance,
        int radius,
        int limit,
        long cooldownMs,
        long locationCooldownMs
    ) {
        this.id = id;
        this.rules = rules;
        this.pack = pack;
        this.chance = chance;
        this.radius = radius;
        this.limit = limit;
        this.cooldownMs = cooldownMs;
        this.locationCooldownMs = locationCooldownMs;
    }

    public boolean matches(SpawnContext ctx) {
        return rules.stream().allMatch(r -> r.matches(ctx));
    }

    public String getId() {
        return id;
    }

    public MobPack getPack() {
        return pack;
    }

    public double getChance() {
        return chance;
    }

    public int getRadius() {
        return radius;
    }

    public int getLimit() {
        return limit;
    }

    public long getCooldownMs() {
        return cooldownMs;
    }

    public long getLocationCooldownMs() {
        return locationCooldownMs;
    }
}


