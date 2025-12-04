package org.dev.nebula;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.bukkit.block.Biome;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
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
import org.dev.nebula.core.menus.MenuListener;
import org.dev.nebula.core.services.ItemsService;
import org.dev.nebula.core.services.SkillsService;
import org.dev.nebula.core.services.UserService;
import org.dev.nebula.core.skills.SkillsManager;
import org.dev.nebula.core.spawn.MobPack;
import org.dev.nebula.core.spawn.SpawnDefinition;
import org.dev.nebula.core.spawn.SpawnManager;
import org.dev.nebula.core.spawn.interfaces.CustomMob;
import org.dev.nebula.core.spawn.mobs.FrostZombie;
import org.dev.nebula.core.spawn.rules.BiomeRule;
import org.dev.nebula.core.spawn.rules.LightRule;

public class NebulaPlugin extends JavaPlugin {
    private DatabaseManager databaseManager;

    private UserService userService;
    private SkillsService skillsService;
    private ItemsService itemsService;

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

        UserDao userDao = new UserDao(databaseManager);
        userService = new UserService(userDao, this);
        SkillDao skillDao = new SkillDao(databaseManager);
        skillsService = new SkillsService(skillDao);
        itemsService = new ItemsService();

        PlayerDataListener playerDataListener = new PlayerDataListener(
            userService, skillsService
        );
        playerDataListener.loadSkills();
        getServer().getPluginManager().registerEvents(playerDataListener, this);

        registerCustomMobs();

        new ItemManager(bus, userService, itemsService).loadItems();
        new SkillsManager(bus, userService, skillsService).registerPassiveSkills();
        new CraftManager(this, itemsService).registerCrafts();

        // test command
        getCommand("adminmenu").setExecutor(
            new AdminMenuCommand(itemsService)
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
        // SpawnManager spawnManager = new SpawnManager();

        // getServer().getPluginManager().registerEvents(
        //         spawnManager, this
        // );

        // CustomMob frost = new FrostZombie();

        // MobPack frostPack = new MobPack(
        //     List.of(frost),
        //     4,
        //     6
        // );

        // SpawnDefinition def = new SpawnDefinition(
        //     "frost_sombie_ID",
        //     List.of(
        //         new BiomeRule(Set.of(Biome.TAIGA, Biome.SNOWY_TAIGA))
        //     ),
        //     frostPack,
        //     0.3,
        //     25,
        //     12,
        //     10_000,
        //     5_000
        // );

        // spawnManager.register(def);
    }
}
