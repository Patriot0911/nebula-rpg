package org.dev.nebula.core.spawn.rules;

import org.dev.nebula.core.spawn.SpawnContext;
import org.dev.nebula.core.spawn.interfaces.SpawnRule;

public class LightRule implements SpawnRule {
    private final int min;

    public LightRule(int min) { this.min = min; }

    @Override
    public boolean matches(SpawnContext ctx) {
        return ctx.lightLevel >= min;
    }

    @Override
    public double getChance() { return 1.0; }
}

