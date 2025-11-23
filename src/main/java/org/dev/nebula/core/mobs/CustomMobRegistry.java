package org.dev.nebula.core.mobs;

import java.util.ArrayList;
import java.util.List;

public class CustomMobRegistry {
    private static final List<CustomMob> mobs = new ArrayList<>();

    public static void register(CustomMob mob) {
        mobs.add(mob);
    }

    public static List<CustomMob> getAll() {
        return mobs;
    }
}
