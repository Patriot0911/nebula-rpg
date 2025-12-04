package org.dev.nebula.core.spawn.interfaces;

import org.dev.nebula.core.spawn.SpawnContext;

public interface SpawnRule {
    boolean matches(SpawnContext ctx);
    double getChance();
}
