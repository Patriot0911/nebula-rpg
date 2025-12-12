package org.dev.nebula.core.spawn.drops;

import java.util.ArrayList;
import java.util.List;

import org.dev.nebula.core.spawn.drops.frozen.FrozenBlockDrop;
import org.dev.nebula.core.spawn.drops.frozen.FrozenSwordDrop;
import org.dev.nebula.core.spawn.interfaces.MobDrop;
import org.dev.nebula.core.spawn.interfaces.MobDropList;
import org.dev.nebula.core.spawn.mobs.frozen.MahahaZombie;

public class MahahaDropList extends MobDropList {

    @Override
    public List<MobDrop> getDropList() {
        return new ArrayList<>(
            List.of(
                new FrozenBlockDrop(),
                new FrozenSwordDrop()
            )
        );
    }

    @Override
    public String getMobKey() {
        return MahahaZombie.MOB_ID;
    }
}
