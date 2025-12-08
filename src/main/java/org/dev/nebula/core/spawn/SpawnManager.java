package org.dev.nebula.core.spawn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.dev.nebula.core.spawn.interfaces.CustomMob;
import org.dev.nebula.core.spawn.packs.FrostZombiePack;

public class SpawnManager implements Listener {
    private final List<SpawnDefinition> definitions = new ArrayList<>(
        List.of(
            new FrostZombiePack().toSpawnDefinition()
        )
    );

    private final Map<String, Long> packCooldown = new HashMap<>();
    private final Map<String, Long> locationCooldown = new HashMap<>();

    @EventHandler
    public void onNaturalSpawn(CreatureSpawnEvent e) {
        if (e.getSpawnReason() != SpawnReason.NATURAL) return;

        SpawnContext ctx = new SpawnContext(e.getLocation());
        Location loc = e.getLocation();

        for (SpawnDefinition def : definitions) {
            if (!def.matches(ctx)) continue;
            if (!canSpawnMore(def, loc)) {
                e.setCancelled(true);
                return;
            }
            if (onCooldown(def)) {
                e.setCancelled(true);
                return;
            }
            if (onLocationCooldown(loc, def)) {
                e.setCancelled(true);
                return;
            }
            if (Math.random() > def.getChance()) continue;

            e.setCancelled(true);

            spawnPack(def, e.getLocation());

            setCooldown(def);
            setLocationCooldown(loc, def);
            System.out.println("spawned: " + def.getId());

            return;
        }
    }

    private boolean onCooldown(SpawnDefinition def) {
        long now = System.currentTimeMillis();
        Long last = packCooldown.get(def.getId());
        if (last == null) return false;

        return now - last < def.getCooldownMs();
    }

    private void setCooldown(SpawnDefinition def) {
        packCooldown.put(def.getId(), System.currentTimeMillis());
    }

    private boolean onLocationCooldown(Location loc, SpawnDefinition def) {
        long now = System.currentTimeMillis();
        String key = getLocKey(loc, def);

        Long last = locationCooldown.get(key);
        if (last == null) return false;

        return now - last < def.getLocationCooldownMs();
    }

    private void setLocationCooldown(Location loc, SpawnDefinition def) {
        String key = getLocKey(loc, def);
        locationCooldown.put(key, System.currentTimeMillis());
    }

    private String getLocKey(Location loc, SpawnDefinition def) {
        int r = def.getRadius();
        int x = loc.getBlockX() / r;
        int z = loc.getBlockZ() / r;
        return def.getId() + ":" + x + ":" + z;
    }

    private boolean canSpawnMore(SpawnDefinition def, Location loc) {
        int radius = def.getRadius();
        int limit = def.getLimit();

        if (limit <= 0) return true;

        int count = 0;
        for (Entity ent : loc.getWorld().getNearbyEntities(loc, radius, radius, radius)) {
            if (ent instanceof LivingEntity) {
                count++;
                if (count >= limit) return false;
            }
        }
        return true;
    }

    private void spawnPack(SpawnDefinition def, Location loc) {
        for (CustomMob mob : def.getPack().roll()) {
            LivingEntity ent = (LivingEntity) loc.getWorld().spawnEntity(
                    loc, mob.getEntityType()
            );
            mob.modifyEntity(ent);
        }
    }
}
