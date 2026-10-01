package de.lalex.craftattack.worldDeactivation;

import de.lalex.craftattack.Main;
import de.lalex.craftattack.util.Message;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

public final class WorldDeactivationListener implements Listener {

    @EventHandler
    public void onPortal(@NotNull PlayerPortalEvent event) {
        Player player = event.getPlayer();
        PlayerTeleportEvent.TeleportCause cause = event.getCause();

        if(cause == PlayerTeleportEvent.TeleportCause.END_PORTAL) {
            boolean open = Main.getInstance().getConfig().getBoolean("worlds-open.end");
            if(!open) {
                event.setCancelled(true);
                Message.send(player, "endClosed");
            }
        }
        if(cause == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            boolean open = Main.getInstance().getConfig().getBoolean("worlds-open.nether");
            if(!open) {
                event.setCancelled(true);
                Message.send(player, "netherClosed");
            }
        }
    }

}
