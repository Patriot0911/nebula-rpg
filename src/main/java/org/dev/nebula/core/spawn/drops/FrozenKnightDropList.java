package org.dev.nebula.core.spawn.drops;

import java.util.ArrayList;
import java.util.List;

import org.dev.nebula.core.spawn.drops.frozen.IceShardDrop;
import org.dev.nebula.core.spawn.interfaces.MobDrop;
import org.dev.nebula.core.spawn.interfaces.MobDropList;
import org.dev.nebula.core.spawn.mobs.frozen.FrozenKnight;

public class FrozenKnightDropList extends MobDropList {

    @Override
    public List<MobDrop> getDropList() {
        return new ArrayList<>(
            List.of(
                new IceShardDrop()
            )
        );
    }

    @Override
    public String getMobKey() {
        return FrozenKnight.MOB_ID;
    }
}
