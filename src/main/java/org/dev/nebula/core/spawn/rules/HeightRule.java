package org.dev.nebula.core.spawn.rules;

import org.dev.nebula.core.spawn.SpawnContext;
import org.dev.nebula.core.spawn.interfaces.SpawnRule;

public class HeightRule implements SpawnRule {
    private final Integer min;
    private final Integer max;

    public HeightRule(Integer min, Integer max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean matches(SpawnContext ctx) {
        return (
            (min == null || ctx.location.getY() >= min) && (max == null || ctx.location.getY() <= max)
        );
    }

    @Override
    public double getChance() { return 1.0; }
}

