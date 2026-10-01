package de.lalex.craftattack.invisibleItemFrame;

import de.lalex.craftattack.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class InvisibleItemFramePlaceListener implements Listener {

    private final JavaPlugin plugin = Main.getPlugin();

    @EventHandler
    public void onPlaceEvent(@NotNull PlayerInteractEvent event) {
        if(!event.hasBlock() || event.getClickedBlock() == null) return;
        ItemStack item = event.getItem();
        if(item == null) return;

        ItemMeta meta = item.getItemMeta();
        if(meta != null && meta.getPersistentDataContainer().has(new NamespacedKey(plugin, InvisibleItemFrameRecipe.INVISIBLE_ITEM_FRAME_KEY), PersistentDataType.BYTE)) {
            Location loc = event.getClickedBlock().getLocation().add(0.5, 1, 0.5);
            ItemFrame frame = event.getPlayer().getWorld().spawn(loc, ItemFrame.class);
            frame.setVisible(false);
            frame.setGlowing(true);
            event.setCancelled(true);

            if(event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                item.setAmount(item.getAmount() - 1);
            }

            Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                if(!frame.isDead()) {
                    frame.setGlowing(false);
                }
            }, 100L);
            event.getPlayer().sendMessage(Component.text("§aUnsichtbares Frame plaziert!"));
        }
    }

}
