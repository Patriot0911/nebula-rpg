package org.dev.nebula.core.spawn;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

public class SpawnContext {
    public final World world;
    public final Location location;
    public final Biome biome;
    public final int lightLevel;
    public final long time;
    public final Player nearestPlayer;

    public SpawnContext(Location loc) {
        this.world = loc.getWorld();
        this.location = loc;
        this.biome = loc.getBlock().getBiome();
        this.lightLevel = loc.getBlock().getLightLevel();
        this.time = world.getTime();
        this.nearestPlayer = world.getNearbyPlayers(loc, 50).stream().findFirst().orElse(null);
    }
}

