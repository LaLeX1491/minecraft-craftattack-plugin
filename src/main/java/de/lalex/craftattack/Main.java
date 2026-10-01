package de.lalex.craftattack;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    private static Main instance;
    public static Logger logger;

    @Override
    public void onEnable() {
        getLogger().info("Initializing Plugin...");

        instance = this;
        logger = getLogger();
        RegistryService.initPlugin();

        logger.info("Plugin loaded successfully!");
    }

    @Override
    public void onDisable() {
        instance = null;
        logger.info("See you soon...");
    }

    public static Main getInstance() {
        return instance;
    }
}
