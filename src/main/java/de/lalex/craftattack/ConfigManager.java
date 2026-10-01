package de.lalex.craftattack.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ConfigManager {

    private final JavaPlugin plugin;
    private FileConfiguration statusesConfig;
    private File statusesFile;

    private FileConfiguration statusPlayersConfig;
    private File statusPlayersFile;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        setup();
    }

    private void setup() {
        File folder =
    }

}
