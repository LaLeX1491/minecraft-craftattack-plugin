package de.lalex.craftattack.util;

import de.lalex.craftattack.Main;
import org.bukkit.entity.Player;

import java.awt.*;

public final class Msg {

    private static final Main main = Main.getInstance();

    private Msg() {
    }



    public static Component sendMessage(Player player, String message) {
        return main.getPrefix()
    }

    public static Component sendMessage(Player player, String pathToMessage, Placeholders[] placeholders) {

    }

    public record Placeholders(String variableName, String variableValue) {}

}
