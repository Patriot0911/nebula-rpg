package org.dev.nebula.core.db;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.dev.nebula.core.db.models.UserData;
import org.dev.nebula.core.services.SkillsService;
import org.dev.nebula.core.services.UsersService;

public class PlayerDataListener implements Listener {
    private final UsersService userService;
    private final SkillsService skillsService;

    public PlayerDataListener(UsersService userService, SkillsService skillsService) {
        this.userService = userService;
        this.skillsService = skillsService;
    }

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();
        String nickname = event.getName();

        UserData userData = userService.loadUser(uuid);

        if (userData == null) {
            userData = userService.createUser(uuid, nickname);
        }

        System.out.println(userData.toString());

        userService.putUserInCache(userData);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        UserData userData = UsersService.getUserData(uuid);
        if (userData == null) return;

        userService.saveUser(userData);
    }

    public void loadSkills() {
        skillsService.loadAllSkills();
    }
}
