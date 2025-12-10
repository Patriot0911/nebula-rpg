package org.dev.nebula.core.spawn.drops;

import java.util.ArrayList;
import java.util.List;

import org.dev.nebula.core.services.ItemsService;
import org.dev.nebula.core.services.UsersService;
import org.dev.nebula.core.spawn.drops.frozen.FrozenBlockDrop;
import org.dev.nebula.core.spawn.drops.frozen.FrozenSwordDrop;
import org.dev.nebula.core.spawn.interfaces.MobDrop;
import org.dev.nebula.core.spawn.interfaces.MobDropList;
import org.dev.nebula.core.spawn.mobs.frozen.MahahaZombie;

public class MahahaDropList extends MobDropList {
    public MahahaDropList(UsersService usersService, ItemsService itemsService) {
        super(usersService, itemsService);
    }

    @Override
    public List<MobDrop> getDropList() {
        return new ArrayList<>(
            List.of(
                new FrozenBlockDrop(usersService, itemsService),
                new FrozenSwordDrop(usersService, itemsService)
            )
        );
    }

    @Override
    public String getMobKey() {
        return MahahaZombie.MOB_ID;
    }
}
