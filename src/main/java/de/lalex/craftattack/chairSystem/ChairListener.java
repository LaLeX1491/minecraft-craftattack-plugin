package de.lalex.craftattack.chairSystem;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.jetbrains.annotations.NotNull;

public final class ChairListener implements Listener {

    @EventHandler
    public void onRightClickStairs(@NotNull PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block block = event.getClickedBlock();
        if (block == null) return;

        Material mat = block.getType();
        if (!mat.name().endsWith("_STAIRS")) return;

        Player player = event.getPlayer();
        if (ChairService.isSitting(player)) return;

        // add 0.5 coordinate units into each direction to be perfectly centered on the stair
        Location loc = block.getLocation().clone().add(0.5, 0.5, 0.5);

        if (block.getBlockData() instanceof Directional directional) {
            BlockFace facing = directional.getFacing();
            // calculates the yaw of the stair to align the foots with the stair
            float yaw = (float) Math.toDegrees(Math.atan2(-facing.getModX(), facing.getModZ()));
            loc.setYaw(yaw + 180f);
        }

        ChairService.sit(player, loc);
        event.setCancelled(true);
    }


    @EventHandler
    public void onLeaveVehicle(@NotNull PlayerToggleSneakEvent event) {
        if(!event.isSneaking()) return;
        Player player = event.getPlayer();

        if(ChairService.isSitting(player))
            ChairService.removeSeat(player);
    }

    @EventHandler
    public void onQuit(@NotNull PlayerQuitEvent event) {
        ChairService.removeSeat(event.getPlayer());
    }
}
