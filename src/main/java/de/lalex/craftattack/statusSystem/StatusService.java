package de.lalex.craftattack.statusSystem;

import de.lalex.craftattack.ConfigRegistry;
import de.lalex.craftattack.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

public final class StatusService {

    private static final FileConfiguration statusesConfig = ConfigRegistry.getInstance().getStatusesConfig();
    private static final FileConfiguration playerStatusConfig = ConfigRegistry.getInstance().getPlayerStatusConfig();

    public static void joinStatus(@NotNull Player player, @NotNull String name) {
        if(statusesConfig.contains("statuses." + name)) {
            Scoreboard teamsScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
            Team team = teamsScoreboard.getTeam("STATUSES_" + name);
            if(team == null) return;

            team.addPlayer(player);
            playerStatusConfig.set(player.getUniqueId() + ".status", name);
            playerStatusConfig.set(player.getUniqueId() + ".joinedAt", Instant.now());
        }
    }

    public static void leaveStatus(@NotNull Player player) {
        if(getStatus(player) == null) return;
    }

    public static @NotNull Team createNewStatus(@NotNull String name, @NotNull String prefix) {
        statusesConfig.set("statuses." + name, prefix);
        ConfigRegistry.getInstance().saveStatusesConfig();

        Scoreboard teamsScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = teamsScoreboard.registerNewTeam("STATUSES_" + name);
        team.prefix(Message.LEGACY.deserialize(prefix));
        return team;
    }

    public static String getStatus(@NotNull Player player) {
        return statusesConfig.getString(player.getUniqueId() + ".status");
    }

    public static String getJoinedAt(@NotNull Player player) {
        return statusesConfig.getString(player.getUniqueId() + ".joinedAt");
    }

    public static void deleteStatus(@NotNull String name) {
        statusesConfig.set("statuses." + name, null);
        ConfigRegistry.getInstance().saveStatusesConfig();

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam("STATUSES_" + name);

        if (team != null) {
            for (String entry : team.getEntries()) {
                team.removeEntry(entry);
            }
            team.unregister();
        }
    }

}
