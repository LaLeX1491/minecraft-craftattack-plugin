package de.lalex.craftattack.worldDeactivation;

import de.lalex.craftattack.Main;
import de.lalex.craftattack.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class WorldDeactivationCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (!player.isOp()) {
            Message.send(player, "noPermission");
            return true;
        }

        String cmd = command.getName().toLowerCase();
        String configPath;

        switch (cmd) {
            case "nether" -> configPath = "worlds-open.nether";
            case "end" -> configPath = "worlds-open.end";
            default -> {
                Message.send(player, "wrongCommandUsage", new Message.Placeholder[]{
                        new Message.Placeholder("%command%", command.getName()),
                        new Message.Placeholder("%args%", "<off | on>")
                });
                return true;
            }
        }

        boolean current = Main.getInstance().getConfig().getBoolean(configPath);

        // no argument - show status
        if (args.length == 0) {
            if(cmd.equals("nether"))
                Message.send(player, current ? "netherOpen" : "netherClosed");
            else
                Message.send(player, current ? "endOpen" : "endClosed");

            return true;
        }

        // one argument -> set status
        if (args.length == 1) {
            String arg = args[0].toLowerCase();
            if (!List.of("on", "off").contains(arg)) {
                Message.send(player, "wrongCommandUsage", new Message.Placeholder[]{
                        new Message.Placeholder("%command%", command.getName()),
                        new Message.Placeholder("%args%", "<off | on>")
                });
                return true;
            }

            boolean enable = arg.equals("on");
            if (current == enable) {
                Message.send(player, enable ? cmd + "AlreadyOpen" : cmd + "AlreadyClosed");
                return true;
            }

            Main.getInstance().getConfig().set(configPath, enable);
            Main.getInstance().saveConfig();

            Message.send(player, enable ? cmd + "NowOpen" : cmd + "NowClosed");
            return true;
        }

        // more than one arg
        Message.send(player, "wrongCommandUsage", new Message.Placeholder[]{
                new Message.Placeholder("%command%", command.getName()),
                new Message.Placeholder("%args%", "<off | on> (optional)")
        });
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String @NotNull [] args
    ) {
        if (args.length == 1) {
            return List.of("on", "off");
        }
        return List.of();
    }
}
