package de.lalex.craftattack.chairSystem;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public final class ChairSystem {

    private static final HashMap<UUID, ArmorStand> seats = new HashMap<>();

    private ChairSystem() {}

    public static void sit(@NotNull Player player, @NotNull Location loc) {
        if(!seats.containsKey(player.getUniqueId())) {
            ArmorStand seat = player.getWorld().spawn(loc, ArmorStand.class, stand -> {
                stand.setInvisible(true);
                stand.setMarker(true);
                stand.setGravity(false);
                stand.setSmall(true);
                stand.setSilent(true);
            });
            seat.addPassenger(player);
            seats.put(player.getUniqueId(), seat);
        }
    }

    public static void sit(@NotNull Player player) {
        sit(player, player.getLocation());
    }

    public static void removeSeat(@NotNull Player player) {
        System.out.println(seats);
        if(seats.containsKey(player.getUniqueId())) {
            ArmorStand stand = seats.remove(player.getUniqueId());
            stand.remove();
        }
    }

    public static boolean isSitting(@NotNull Player player) {
        return seats.containsKey(player.getUniqueId());
    }

}
