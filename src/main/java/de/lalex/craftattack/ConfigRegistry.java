package de.lalex.craftattack;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class ConfigRegistry {

    private static ConfigRegistry instance;

    private final ConfigManager cfgManager;

    private final FileConfiguration messagesConfig;
    private final FileConfiguration dataConfig;
    private final FileConfiguration statusesConfig;
    private final FileConfiguration playerStatusConfig;

    private ConfigRegistry(JavaPlugin plugin) {
        this.cfgManager = new ConfigManager(plugin);

        plugin.saveDefaultConfig();
        cfgManager.loadConfig("messages.yml");
        messagesConfig = cfgManager.getConfig("messages.yml");
        cfgManager.loadConfig("data.yml");
        dataConfig = cfgManager.getConfig("data.yml");
        cfgManager.loadConfig("status/statuses.yml");
        statusesConfig = cfgManager.getConfig("status/statuses.yml");
        cfgManager.loadConfig("status/players.yml");
        playerStatusConfig = cfgManager.getConfig("status/players.yml");
    }

    public static void init(JavaPlugin plugin) {
        if (instance == null) {
            instance = new ConfigRegistry(plugin);
        }
    }

    public static ConfigRegistry getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ConfigRegistry not initialized! Call init(plugin) first.");
        }
        return instance;
    }

    public FileConfiguration getDataConfig() {
        return dataConfig;
    }

    public FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }

    public FileConfiguration getStatusesConfig() {
        return statusesConfig;
    }

    public FileConfiguration getPlayerStatusConfig() {
        return playerStatusConfig;
    }

    public void saveDataConfig() {
        cfgManager.saveConfig("data.yml");
    }

    public void saveMessagesConfig() {
        cfgManager.saveConfig("messages.yml");
    }

    public void saveStatusesConfig() {
        cfgManager.saveConfig("status/statuses.yml");
    }

    public void savePlayerStatusConfig() {
        cfgManager.saveConfig("status/players.yml");
    }

    public void saveAll() {
        cfgManager.saveAll();
    }
}
