package org.dev.nebula;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.dev.nebula.core.achievements.AchievementManager;
import org.dev.nebula.core.bridge.CoreEventBridgeListener;
import org.dev.nebula.core.commands.AdminMenuCommand;
import org.dev.nebula.core.crafts.CraftManager;
import org.dev.nebula.core.db.DatabaseConfig;
import org.dev.nebula.core.db.DatabaseManager;
import org.dev.nebula.core.db.DatabaseMigrator;
import org.dev.nebula.core.db.PlayerDataListener;
import org.dev.nebula.core.db.dao.SkillDao;
import org.dev.nebula.core.db.dao.UserDao;
import org.dev.nebula.core.events.EventBus;
import org.dev.nebula.core.events.NebulaEventBus;
import org.dev.nebula.core.items.ItemManager;
import org.dev.nebula.core.menus.MenuRegistry;
import org.dev.nebula.core.services.AchievementsService;
import org.dev.nebula.core.services.ItemsService;
import org.dev.nebula.core.services.MobsService;
import org.dev.nebula.core.services.SkillsService;
import org.dev.nebula.core.services.UsersService;
import org.dev.nebula.core.skills.SkillsManager;
import org.dev.nebula.core.spawn.SpawnListener;
import org.dev.nebula.core.spawn.SpawnManager;

public class NebulaPlugin extends JavaPlugin {
    private DatabaseManager databaseManager;

    private UsersService userService;
    private SkillsService skillsService;
    private ItemsService itemsService;
    private AchievementsService achievementsService;
    private MobsService mobsService;

    @Override
    public void onEnable() {
        saveDefaultConfigs();
        connectToDatabase();

        EventBus bus = new NebulaEventBus();

        UserDao userDao = new UserDao(databaseManager);
        userService = new UsersService(userDao, this);
        SkillDao skillDao = new SkillDao(databaseManager);
        skillsService = new SkillsService(skillDao);
        itemsService = new ItemsService();
        achievementsService = new AchievementsService();
        mobsService = new MobsService();

        getServer().getPluginManager().registerEvents(
                new CoreEventBridgeListener(bus), this
        );
        getServer().getPluginManager().registerEvents(
                new MenuRegistry(), this
        );
        getServer().getPluginManager().registerEvents(
                new SpawnListener(mobsService), this
        );

        PlayerDataListener playerDataListener = new PlayerDataListener(
            userService, skillsService
        );
        playerDataListener.loadSkills();
        getServer().getPluginManager().registerEvents(playerDataListener, this);

        new ItemManager(bus, itemsService).loadItems();

        new SpawnManager(mobsService).registerDropList();
        new SkillsManager(bus, skillsService).registerPassiveSkills();
        new CraftManager(this).registerCrafts();
        new AchievementManager(bus, achievementsService).registerAchievements();

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
            return;
        }
    }

    public void saveDefaultConfigs() {
        saveResource("db.yml", false);
    }
}
