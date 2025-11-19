package org.dev.nebula;

import java.io.File;

import javax.imageio.spi.ServiceRegistry;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.dev.nebula.core.bridge.CoreEventBridgeListener;
import org.dev.nebula.core.db.DatabaseConfig;
import org.dev.nebula.core.db.DatabaseManager;
import org.dev.nebula.core.db.dao.UserDao;
import org.dev.nebula.core.eventBus.EventBus;
import org.dev.nebula.core.eventBus.NebulaEventBus;
import org.dev.nebula.core.mobSpawn.CustomMobRegistry;
import org.dev.nebula.core.mobSpawn.CustomMobSpawnListener;
import org.dev.nebula.core.mobSpawn.mobs.FrostZombie;
import org.dev.nebula.core.services.UserService;
import org.dev.nebula.core.skills.SkillsManager;

public class NebulaPlugin extends JavaPlugin {
    private DatabaseManager databaseManager;

    private UserService userService;

    @Override
    public void onEnable() {

        connectToDatabase();

        EventBus bus = new NebulaEventBus();

        getServer().getPluginManager().registerEvents(
                new CoreEventBridgeListener(bus), this
        );
        getServer().getPluginManager().registerEvents(
                new CustomMobSpawnListener(), this
        );

        UserDao userDao = new UserDao(databaseManager);
        userService = new UserService(userDao);

        registerCustomMobs();

        new SkillsManager(bus, userService).registerPassiveSkills();
    }

    public void connectToDatabase() {
        File dbFile = new File(getDataFolder(), "db.yml");
        YamlConfiguration dbCfg = YamlConfiguration.loadConfiguration(dbFile);

        DatabaseConfig dbConfig = new DatabaseConfig(dbCfg);

        databaseManager = new DatabaseManager();

        try {
            databaseManager.connect(dbConfig);
            getLogger().info("Database connected successfully");
        } catch (Exception e) {
            getLogger().severe("Failed to connect to database: " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    public void registerCustomMobs() {
        CustomMobRegistry.register(
            new FrostZombie()
        );
    }
}
