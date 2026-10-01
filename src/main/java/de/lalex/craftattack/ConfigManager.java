package de.lalex.craftattack;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class ConfigManager {

    private final JavaPlugin plugin;
    private final Map<String, FileConfiguration> configs = new HashMap<>();
    private final Map<String, File> files = new HashMap<>();

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadConfig(@NotNull String path) {
        File file = new File(plugin.getDataFolder(), path);
        File parent = file.getParentFile();
        if (!parent.exists()) parent.mkdirs();

        if(!file.exists()) plugin.saveResource(path, false);

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        configs.put(path, config);
        files.put(path, file);
    }

    public FileConfiguration getConfig(String path) {
        return configs.get(path);
    }

    public void saveConfig(@NotNull String path) {
        FileConfiguration cfg = configs.get(path);
        File file = files.get(path);
        if (cfg == null || file == null) return;

        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAll() {
        for (String path : configs.keySet()) {
            saveConfig(path);
        }
    }


}
