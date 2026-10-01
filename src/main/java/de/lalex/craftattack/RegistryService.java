package de.lalex.craftattack;

import de.lalex.craftattack.anvilRename.AnvilRenameListener;
import de.lalex.craftattack.chairSystem.ChairListener;
import de.lalex.craftattack.chairSystem.SitCommand;
import de.lalex.craftattack.commands.HatCommand;
import de.lalex.craftattack.invisibleItemFrame.InvisibleItemFrameListener;
import de.lalex.craftattack.invisibleItemFrame.InvisibleItemFrameRecipe;
import de.lalex.craftattack.originalDragonEgg.DragonEggManager;
import de.lalex.craftattack.worldDeactivation.WorldDeactivationCommand;
import de.lalex.craftattack.worldDeactivation.WorldDeactivationListener;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Objects;

public final class RegistryService {

    private static final Main main = Main.getInstance();

    public static void initPlugin() {
        ConfigRegistry.init(main);

        addDeathCounter();
        registerCustomRecipes();
        registerCommands();
        registerEventListener();
    }

    private static void addDeathCounter() {
        Scoreboard deathScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Objective deaths = deathScoreboard.getObjective("deaths");
        if (deaths == null) {
            deaths = deathScoreboard.registerNewObjective("deaths", Criteria.DEATH_COUNT, Component.text(""));
            deaths.setDisplaySlot(DisplaySlot.PLAYER_LIST);
            Main.logger.info("New deaths scoreboard initialized");
        }
    }

    private static void registerCustomRecipes() {
        InvisibleItemFrameRecipe.registerInvisibleItemFrameRecipe();
    }

    private static void registerCommands() {
        Objects.requireNonNull(main.getCommand("sit"), "Sit command not found!")
                .setExecutor(new SitCommand());
        Objects.requireNonNull(main.getCommand("hat"), "Chair command not found!")
                .setExecutor(new HatCommand());

        WorldDeactivationCommand wdc = new WorldDeactivationCommand();
        Objects.requireNonNull(
                main.getCommand("end")).setExecutor(wdc);
        Objects.requireNonNull(
                main.getCommand("end")).setTabCompleter(wdc);
        Objects.requireNonNull(
                main.getCommand("nether")).setExecutor(wdc);
        Objects.requireNonNull(
                main.getCommand("nether")).setTabCompleter(wdc);

    }

    private static void registerEventListener() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new InvisibleItemFrameListener(), main);
        pm.registerEvents(new ChairListener(), main);
        pm.registerEvents(new WorldDeactivationListener(), main);
        pm.registerEvents(new AnvilRenameListener(), main);
        pm.registerEvents(new DragonEggManager(), main);
    }

}
