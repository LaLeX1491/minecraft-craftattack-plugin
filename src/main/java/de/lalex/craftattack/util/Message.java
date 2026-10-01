package de.lalex.craftattack.util;

import de.lalex.craftattack.ConfigRegistry;
import de.lalex.craftattack.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class Message {

    public static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacyAmpersand();
    public static final LegacyComponentSerializer SECTION = LegacyComponentSerializer.legacySection();

    public record Placeholder(String key, String value) {}

    @Contract(pure = true)
    public static void send(Player player, String pathToMessage, Placeholder @NotNull [] placeholders) {

        String message = ConfigRegistry.getInstance()
                .getMessagesConfig()
                .getString(pathToMessage);

        if (message == null) return;

        // default placeholder
        message = message.replaceAll("%player%", player.getName());
        message = message.replaceAll("%prefix%", LEGACY.serialize(getPrefix()));

        // custom placeholder
        for (Placeholder ph : placeholders) {
            message = message.replace(ph.key, ph.value);
        }

        // split lines
        Component finalComp = Component.empty();
        if (message.contains("\n")) {
            String[] lines = message.split("\n", -1);
            for (int i = 0; i < lines.length; i++) {
                finalComp = finalComp.append(LEGACY.deserialize(lines[i]));
                if (i < lines.length - 1) {
                    finalComp = finalComp.append(Component.newline());
                }
            }
        } else {
            finalComp = LEGACY.deserialize(message);
        }

        player.sendMessage(finalComp);
    }

    @Contract(pure = true)
    public static void send(Player player, String pathToMessage) {
        send(player, pathToMessage, new Placeholder[0]);
    }

    public static @NotNull Component get(String message, boolean p) {
        if(p) return getPrefix().append(LEGACY.deserialize(message));
        return LEGACY.deserialize(message);
    }

    public static @NotNull Component get(String message) {
        return get(message, true);
    }

    public static @NotNull Component getPrefix() {
        String prefix = Main.getInstance().getConfig().getString("prefix");
        if(prefix == null || prefix.isEmpty())
            return Component.empty();
        return LEGACY.deserialize(prefix);
    }

}
