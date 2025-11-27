package org.dev.nebula;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.dev.nebula.core.bridge.CoreEventBridgeListener;
import org.dev.nebula.core.commands.AdminMenuCommand;
import org.dev.nebula.core.db.DatabaseConfig;
import org.dev.nebula.core.db.DatabaseManager;
import org.dev.nebula.core.db.DatabaseMigrator;
import org.dev.nebula.core.db.PlayerDataListener;
import org.dev.nebula.core.db.dao.SkillDao;
import org.dev.nebula.core.db.dao.UserDao;
import org.dev.nebula.core.eventBus.EventBus;
import org.dev.nebula.core.eventBus.NebulaEventBus;
import org.dev.nebula.core.items.ItemManager;
import org.dev.nebula.core.menus.MenuListener;
import org.dev.nebula.core.mobs.CustomMobRegistry;
import org.dev.nebula.core.mobs.CustomMobSpawnListener;
import org.dev.nebula.core.mobs.custom.FrostZombie;
import org.dev.nebula.core.services.ItemsService;
import org.dev.nebula.core.services.SkillsService;
import org.dev.nebula.core.services.UserService;
import org.dev.nebula.core.skills.SkillsManager;

public class NebulaPlugin extends JavaPlugin {
    private DatabaseManager databaseManager;

    private UserService userService;
    private SkillsService skillsService;
    private ItemsService ItemsService;

    @Override
    public void onEnable() {
        saveDefaultConfigs();
        connectToDatabase();

        EventBus bus = new NebulaEventBus();

        getServer().getPluginManager().registerEvents(
                new CoreEventBridgeListener(bus), this
        );
        getServer().getPluginManager().registerEvents(
                new MenuListener(), this
        );
        getServer().getPluginManager().registerEvents(
                new CustomMobSpawnListener(), this
        );

        UserDao userDao = new UserDao(databaseManager);
        userService = new UserService(userDao);
        SkillDao skillDao = new SkillDao(databaseManager);
        skillsService = new SkillsService(skillDao);
        ItemsService = new ItemsService();

        PlayerDataListener playerDataListener = new PlayerDataListener(
            userService, skillsService
        );
        playerDataListener.loadSkills();
        getServer().getPluginManager().registerEvents(playerDataListener, this);

        registerCustomMobs();

        new ItemManager(bus, userService, ItemsService).registerItems();
        new SkillsManager(bus, userService, skillsService).registerPassiveSkills();

        // test command
        getCommand("adminmenu").setExecutor(
            new AdminMenuCommand()
        );
    }

    public void connectToDatabase() {
        File dbFile = new File(getDataFolder(), "db.yml");
        YamlConfiguration dbCfg = YamlConfiguration.loadConfiguration(dbFile);

        DatabaseConfig dbConfig = new DatabaseConfig(dbCfg);

        try {
            DatabaseMigrator.migrate(dbConfig, this.getClassLoader());

            databaseManager = new DatabaseManager();
            databaseManager.connect(dbConfig);
            getLogger().info("Database connected successfully");
        } catch (Exception e) {
            getLogger().severe("Failed to connect to database: " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    public void saveDefaultConfigs() {
        saveResource("db.yml", false);
    }

    public void registerCustomMobs() {
        CustomMobRegistry.register(
            new FrostZombie()
        );
    }
}
