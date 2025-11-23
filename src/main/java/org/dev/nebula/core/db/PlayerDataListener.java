package org.dev.nebula.core.db;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.dev.nebula.core.db.models.UserData;
import org.dev.nebula.core.services.SkillsService;
import org.dev.nebula.core.services.UserService;

public class PlayerDataListener implements Listener {
    private final UserService userService;
    private final SkillsService skillsService;

    public PlayerDataListener(UserService userService, SkillsService skillsService) {
        this.userService = userService;
        this.skillsService = skillsService;
    }

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();
        String nickname = event.getName();

        UserData userData = userService.loadUser(uuid);

        if (userData == null) {
            userData = userService.createUser(nickname);
            Bukkit.getLogger().info("Created new user: " + nickname);
        } else {
            Bukkit.getLogger().info("Loaded user data for: " + nickname);
        }

        userService.putUserInCache(userData);
    }

    public void loadSkills() {
        skillsService.loadAllSkills();
    }
}
