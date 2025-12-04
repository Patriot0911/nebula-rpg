package org.dev.nebula.core.spawn.rules;

import java.util.Set;

import org.bukkit.block.Biome;
import org.dev.nebula.core.spawn.SpawnContext;
import org.dev.nebula.core.spawn.interfaces.SpawnRule;

public class BiomeRule implements SpawnRule {
    private final Set<Biome> biomes;

    public BiomeRule(Set<Biome> biomes) {
        this.biomes = biomes;
    }

    public boolean matches(SpawnContext ctx) {
        return biomes.contains(ctx.biome);
    }

    public double getChance() { return 1.0; }
}

