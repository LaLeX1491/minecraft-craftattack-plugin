package de.lalex.craftattack.originalDragonEgg;

import de.lalex.craftattack.ConfigRegistry;
import de.lalex.craftattack.Main;
import de.lalex.craftattack.util.ItemBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;


public final class DragonEggManager implements Listener {

    private static final NamespacedKey ORIGINAL_EGG_KEY = new NamespacedKey(Main.getInstance(), "original_dragon_egg");

    private static final FileConfiguration data = ConfigRegistry.getInstance().getDataConfig();

    public static @NotNull ItemStack getUniqueDragonEgg() {
        ItemStack dragonEgg = new ItemBuilder(Material.DRAGON_EGG)
                .setLore(Main.getInstance().getConfig().getString("dragon-egg.lore", "&aDas originale Ei!"))
                .build();
        dragonEgg.getItemMeta().getPersistentDataContainer().set(ORIGINAL_EGG_KEY, PersistentDataType.STRING, UUID.randomUUID().toString());
        return dragonEgg;
    }

    public static boolean isOriginalDragonEgg(ItemStack item) {
        if(item == null || item.getType() != Material.DRAGON_EGG) return false;
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return false;
        return meta.getPersistentDataContainer().has(ORIGINAL_EGG_KEY, PersistentDataType.STRING);
    }

    @EventHandler
    public void onEnderDragonDeath(@NotNull EntityDeathEvent event) {
        if(!(event.getEntity() instanceof EnderDragon dragon)) return;

        Location eggLoc = dragon.getWorld().getHighestBlockAt(dragon.getWorld().getSpawnLocation()).getLocation();
        if(eggLoc.getBlock().getType() != Material.DRAGON_EGG) return;

        data.set("dragon-egg.location", eggLoc);
        ConfigRegistry.getInstance().saveDataConfig();
    }

    @EventHandler
    public void onDragonEggTeleport(@NotNull PlayerInteractEvent event) {
        if(event.getClickedBlock() == null) return;
        if(event.getClickedBlock().getType() != Material.DRAGON_EGG) return;

        Location oldLoc = Main.getInstance().getConfig().getLocation("dragon-egg.location");
        if(oldLoc != null && oldLoc.equals(event.getClickedBlock().getLocation())) {
            Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                Location newLoc = findNewEggLocation(event.getPlayer().getWorld());
                if(newLoc != null) {
                    data.set("dragon-egg.location", newLoc);
                    ConfigRegistry.getInstance().saveDataConfig();
                }
            });
        }
    }

    @EventHandler
    public void onEggDrop(@NotNull ItemSpawnEvent event) {
        ItemStack item = event.getEntity().getItemStack();
        if (item.getType() != Material.DRAGON_EGG) return;

        Location originalLoc = Main.getInstance().getConfig().getLocation("dragon-egg.location");

        if (originalLoc != null && originalLoc.distance(event.getLocation()) < 2) {
            event.setCancelled(true);
            event.getEntity().getWorld().dropItemNaturally(event.getLocation(), getUniqueDragonEgg());
            event.getEntity().remove();
        }
    }

    private static @Nullable Location findNewEggLocation(@NotNull World world) {
        for (Chunk chunk : world.getLoadedChunks()) {
            // So we check every block in the chunk (fast enough in the end)
            int bx = chunk.getX() << 4;
            int bz = chunk.getZ() << 4;

            for (int x = bx; x < bx + 16; x++) {
                for (int y = world.getMinHeight(); y < world.getMaxHeight(); y++) {
                    for (int z = bz; z < bz + 16; z++) {
                        Block b = world.getBlockAt(x, y, z);
                        if (b.getType() == Material.DRAGON_EGG) {
                            return b.getLocation();
                        }
                    }
                }
            }
        }

        return null;
    }

}
