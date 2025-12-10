package org.dev.nebula.core.spawn.interfaces;

import java.util.List;
import java.util.UUID;

import org.bukkit.event.entity.EntityDeathEvent;
import org.dev.nebula.core.services.ItemsService;
import org.dev.nebula.core.services.UsersService;

public abstract class MobDropList {
    protected UsersService usersService;
    protected ItemsService itemsService;

    public MobDropList(UsersService usersService, ItemsService itemsService) {
        this.usersService = usersService;
        this.itemsService = itemsService;
    }

    public boolean getPreventDefault() {
        return false;
    }

    public abstract List<MobDrop> getDropList();
    public abstract String getMobKey();
    public Integer getDropXp(EntityDeathEvent event) {
        return null;
    };
    public void operateUser(UUID userId) {};
}
