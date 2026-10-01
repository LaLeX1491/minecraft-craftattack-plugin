package de.lalex.craftattack.commands;

import de.lalex.craftattack.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(sender instanceof Player player) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if(!item.getType().isAir()) {
                ItemStack oldHelmet = player.getInventory().getHelmet();
                player.getInventory().setHelmet(item);
                player.getInventory().setItemInMainHand(oldHelmet);
                Message.send(player, "hat.successfully-switched");
            } else
                Message.send(player, "hat.no-item-in-hand");
        }
        return true;
    }
}
