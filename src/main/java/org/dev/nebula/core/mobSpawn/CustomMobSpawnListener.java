package org.dev.nebula.core.mobSpawn;

import java.util.List;

import org.bukkit.block.Biome;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CustomMobSpawnListener implements Listener {
    @EventHandler
    public void onNaturalSpawn(CreatureSpawnEvent e) {
        if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL) return;

        Biome biome = e.getLocation().getBlock().getBiome();

        List<CustomMob> candidates = CustomMobRegistry.getAll().stream()
                .filter(m -> m.getAllowedBiomes().contains(biome))
                .toList();

        if (candidates.isEmpty()) return;

        for (CustomMob mob : candidates) {
            if (Math.random() <= mob.getSpawnChance()) {
                e.setCancelled(true);
                spawnCustomMob(e, mob);
                return;
            }
        }
    }

    private void spawnCustomMob(CreatureSpawnEvent e, CustomMob mob) {
        LivingEntity ent = (LivingEntity) e.getLocation().getWorld()
                .spawnEntity(e.getLocation(), mob.getEntityType());

        mob.modifyEntity(ent);
    }
}
