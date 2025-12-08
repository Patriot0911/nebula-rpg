package org.dev.nebula.core.spawn.packs;

import java.util.List;
import java.util.Set;

import org.bukkit.block.Biome;
import org.dev.nebula.core.spawn.MobPack;
import org.dev.nebula.core.spawn.interfaces.CustomMob;
import org.dev.nebula.core.spawn.interfaces.SpawnRule;
import org.dev.nebula.core.spawn.mobs.frozen.MahahaZombie;
import org.dev.nebula.core.spawn.rules.BiomeRule;
import org.dev.nebula.core.spawn.rules.HeightRule;

public class FrostZombiePack extends MobPack {
    private static final List<CustomMob> packMobs = List.of(
        new MahahaZombie()
    );
    private static final int minCount = 2;
    private static final int maxCount = 6;

    public FrostZombiePack() {
        super(FrostZombiePack.packMobs, FrostZombiePack.minCount, FrostZombiePack.maxCount);
    }

    public List<SpawnRule> getSpawnRules() {
        return List.of(
            new HeightRule(60, 140),
            new BiomeRule(
                Set.of(
                    Biome.TAIGA,
                    Biome.SNOWY_TAIGA,
                    Biome.SNOWY_BEACH,
                    Biome.SNOWY_PLAINS,
                    Biome.SNOWY_SLOPES,
                    Biome.ICE_SPIKES
                )
            )
        );
    }
    public String getPackId() {
        return "frost_zombie_pack_01";
    }
    public double getSpawnChance() {
        return 1;
    }
    public int getSpawnRadius() {
        return 50;
    }
    public int getSpawnLimit() {
        return 15;
    }
    public long getSpawnCooldown() {
        return 2_000;
    }
    public long getSpawnLocCooldown() {
        return 6_000;
    }
}
