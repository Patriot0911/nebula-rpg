package org.dev.nebula.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.NamespacedKey;
import org.dev.nebula.core.spawn.SpawnDefinition;
import org.dev.nebula.core.spawn.drops.FrozenKnightDropList;
import org.dev.nebula.core.spawn.drops.MahahaDropList;
import org.dev.nebula.core.spawn.interfaces.MobDropList;
import org.dev.nebula.core.spawn.packs.FrostElitePack;
import org.dev.nebula.core.spawn.packs.FrostZombiePack;

public class MobsService {
    public static final NamespacedKey MOB_ID = new NamespacedKey("nebula", "mob_id");

    private final List<SpawnDefinition> definitions = new ArrayList<>(
        List.of(
            new FrostZombiePack().toSpawnDefinition(),
            new FrostElitePack().toSpawnDefinition()
        )
    );

    private final List<Class<? extends MobDropList>> mobDropList = new ArrayList<Class<? extends MobDropList>>(
        List.of(
            MahahaDropList.class,
            FrozenKnightDropList.class
        )
    );
    private final Map<String, MobDropList> mobDrops = new HashMap<>();

    private final Map<String, Long> packCooldown = new HashMap<>();
    private final Map<String, Long> locationCooldown = new HashMap<>();

    public MobsService() {}

    public List<Class<? extends MobDropList>> getMobDropsList() {
        return mobDropList;
    }
    public void registerMobDrop(String key, MobDropList item) {
        mobDrops.put(key, item);
    }
    public Map<String, MobDropList> getMobDrops() {
        return mobDrops;
    }

    public List<SpawnDefinition> getSpawnDefinitions() {
        return this.definitions;
    }

    public Long getPackCooldown(String key) {
        return this.packCooldown.get(key);
    }
    public void setPackCooldown(String key, Long ms) {
        this.packCooldown.put(key, ms);
    }

    public Long getLocalePackCooldown(String key) {
        return this.locationCooldown.get(key);
    }
    public void setLocalePackCooldown(String key, Long ms) {
        this.locationCooldown.put(key, ms);
    }
}
