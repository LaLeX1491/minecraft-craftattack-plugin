package de.lalex.craftattack.originalDragonEgg;

import de.lalex.craftattack.Main;
import de.lalex.craftattack.util.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;


public final class OriginalDragonEggListener implements Listener {

    private static final NamespacedKey DRAGON_EGG_KEY = new NamespacedKey(Main.getInstance(), "original_dragon_egg");

    public static @NotNull ItemStack getUniqueDragonEgg() {
        ItemStack dragonEgg = new ItemBuilder(Material.DRAGON_EGG)
                .setLore(
                        Main.getInstance().getConfig().getString("dragon-egg.lore") == null ?
                                "" :  Main.getInstance().getConfig().getString("dragon-egg.lore")
                )
                .build();
        dragonEgg.getItemMeta().getPersistentDataContainer().set(DRAGON_EGG_KEY, PersistentDataType.BYTE, (byte) 1);
        return dragonEgg;
    }

    @EventHandler
    public void onEnderDragonDeath(EntityDeathEvent event) {
        if(!(event.getEntity() instanceof EnderDragon dragon)) return;

        Location eggLoc = dragon.getWorld().getHighestBlockAt(dragon.getWorld().getSpawnLocation()).getLocation();
    }

}
