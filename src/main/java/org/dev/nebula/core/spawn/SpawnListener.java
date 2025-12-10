package org.dev.nebula.core.spawn;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.persistence.PersistentDataType;
import org.dev.nebula.core.services.MobsService;
import org.dev.nebula.core.spawn.interfaces.CustomMob;
import org.dev.nebula.core.spawn.interfaces.MobDrop;
import org.dev.nebula.core.spawn.interfaces.MobDropList;

public class SpawnListener implements Listener {
    private MobsService mobsService;

    public SpawnListener(MobsService mobsService) {
        this.mobsService = mobsService;
    }

    @EventHandler
    public void onNaturalSpawn(CreatureSpawnEvent e) {
        if (e.getSpawnReason() != SpawnReason.NATURAL) return;

        SpawnContext ctx = new SpawnContext(e.getLocation());
        Location loc = e.getLocation();

        for (SpawnDefinition def : mobsService.getSpawnDefinitions()) {
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

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity instanceof Player) return;

        var persistenceDataContainer = entity.getPersistentDataContainer();

        if (persistenceDataContainer == null || !persistenceDataContainer.has(MobsService.MOB_ID, PersistentDataType.STRING)) return;

        String mobId = persistenceDataContainer.get(MobsService.MOB_ID, PersistentDataType.STRING);

        MobDropList mobDropList = mobsService.getMobDrops().get(mobId);

        if (mobDropList == null) return;

        if (mobDropList.getPreventDefault()) {
            event.getDrops().clear();
        }

        if (mobDropList.getDropList() != null) {
            for (MobDrop mobDrop : mobDropList.getDropList()) {
                if (Math.random() >= mobDrop.getChance(event)) continue;
                event.getDrops().add(mobDrop.getItem(event));
            }
        }

        Integer dropXp = mobDropList.getDropXp(event);

        if (dropXp != null) {
            event.setDroppedExp(dropXp);
        }

        if (entity.getKiller() instanceof Player player) {
            UUID userId = player.getUniqueId();
            mobDropList.operateUser(userId);
        }
    }

    private boolean onCooldown(SpawnDefinition def) {
        long now = System.currentTimeMillis();
        Long last = mobsService.getPackCooldown(def.getId());
        if (last == null) return false;

        return now - last < def.getCooldownMs();
    }

    private void setCooldown(SpawnDefinition def) {
        mobsService.setPackCooldown(def.getId(), System.currentTimeMillis());
    }

    private boolean onLocationCooldown(Location loc, SpawnDefinition def) {
        long now = System.currentTimeMillis();
        String key = getLocKey(loc, def);

        Long last = mobsService.getLocalePackCooldown(key);
        if (last == null) return false;

        return now - last < def.getLocationCooldownMs();
    }

    private void setLocationCooldown(Location loc, SpawnDefinition def) {
        String key = getLocKey(loc, def);
        mobsService.setLocalePackCooldown(key, System.currentTimeMillis());
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
