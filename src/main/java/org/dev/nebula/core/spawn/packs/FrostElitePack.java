package org.dev.nebula.core.spawn.packs;

import java.util.List;
import java.util.Set;

import org.bukkit.block.Biome;
import org.dev.nebula.core.spawn.interfaces.CustomMob;
import org.dev.nebula.core.spawn.interfaces.MobPack;
import org.dev.nebula.core.spawn.interfaces.SpawnRule;
import org.dev.nebula.core.spawn.mobs.frozen.FrozenKnight;
import org.dev.nebula.core.spawn.mobs.frozen.MahahaZombie;
import org.dev.nebula.core.spawn.rules.BiomeRule;
import org.dev.nebula.core.spawn.rules.HeightRule;

public class FrostElitePack extends MobPack {
    private static final List<CustomMob> packMobs = List.of(
        new MahahaZombie(),
        new FrozenKnight()
    );
    private static final int minCount = 1;
    private static final int maxCount = 6;

    public FrostElitePack() {
        super(FrostElitePack.packMobs, FrostElitePack.minCount, FrostElitePack.maxCount);
    }

    public List<SpawnRule> getSpawnRules() {
        return List.of(
            new HeightRule(60, 140),
            new BiomeRule(
                Set.of(
                    Biome.ICE_SPIKES
                )
            )
        );
    }
    public String getPackId() {
        return "frost_elite_pack";
    }
    public double getSpawnChance() {
        return 0.8;
    }
    public int getSpawnRadius() {
        return 50;
    }
    public int getSpawnLimit() {
        return 15;
    }
    public long getSpawnCooldown() {
        return 30_000;
    }
    public long getSpawnLocCooldown() {
        return 50_000;
    }
}
