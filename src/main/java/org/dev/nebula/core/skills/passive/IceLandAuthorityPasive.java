package org.dev.nebula.core.skills.passive;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.dev.nebula.core.db.models.SkillData;
import org.dev.nebula.core.db.models.UserData;
import org.dev.nebula.core.events.EventBus;
import org.dev.nebula.core.events.busEvents.TargetByEntityEvent;
import org.dev.nebula.core.events.busEvents.damage.DamageTakeEvent;
import org.dev.nebula.core.services.MobsService;
import org.dev.nebula.core.services.UsersService;
import org.dev.nebula.core.skills.PassiveSkillBase;
import org.dev.nebula.core.spawn.mobs.frozen.MahahaZombie;

public class IceLandAuthorityPasive extends PassiveSkillBase {
    public static final String SKILL_NAME = "iceLandAuthority";
    private final HashMap<String, Integer> mobsForAuthority = new HashMap<String, Integer>(
        // Mob - level
        Map.of(
            MahahaZombie.MOB_ID, 1
        )
    );

    public IceLandAuthorityPasive(EventBus bus) {
        super(bus);
        bus.subscribe(TargetByEntityEvent.class, this::onTargetEvent);
        subscribeSkillEvent(DamageTakeEvent.class, this::onDamageTake, SKILL_NAME);
    }

    public void onDamageTake(DamageTakeEvent e, UserData userData) {
        if (!(e.event.getEntity() instanceof Mob entity)) return;

        SkillData userSkillData = userData.getSkill(SKILL_NAME);
        var persistenceDataContainer = entity.getPersistentDataContainer();
        String mobId = persistenceDataContainer.get(MobsService.MOB_ID, PersistentDataType.STRING);
        if (mobId == null || mobsForAuthority.get(mobId) > userSkillData.getLevel()) return;

        entity.setTarget(null);
        e.event.setCancelled(true);
    }
    public void onTargetEvent(TargetByEntityEvent e) {
        if (!(e.event.getEntity() instanceof Mob entity)) return;
        if (!(entity.getTarget() instanceof Player player)) return;

        UserData userData = UsersService.getUserData(player.getUniqueId());
        SkillData userSkillData = userData.getSkill(SKILL_NAME);
        if (userSkillData == null) return;
        var persistenceDataContainer = entity.getPersistentDataContainer();
        String mobId = persistenceDataContainer.get(MobsService.MOB_ID, PersistentDataType.STRING);
        Integer mobReqLevel = mobsForAuthority.get(mobId);
        if (
            mobId == null || mobReqLevel > userSkillData.getLevel()
        ) return;

        if (userSkillData.getLevel() >= mobReqLevel + 5) {
            int searchChunksRadius = 1;
            List<Entity> nearbyEntities = entity.getNearbyEntities(searchChunksRadius, searchChunksRadius, searchChunksRadius);
            for (var nearEntity : nearbyEntities) {
                if (!(nearEntity instanceof Mob nearMob)) continue;
                if (nearMob.isAggressive() && nearMob != entity) {
                    e.event.setTarget(nearMob);
                    e.event.setCancelled(true);
                    break;
                }
            }
        }
    }
}
