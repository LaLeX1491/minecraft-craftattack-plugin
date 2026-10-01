package de.lalex.craftattack.invisibleItemFrame;

import org.bukkit.GameMode;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public final class InvisibleItemFrameListener implements Listener {

    @EventHandler
    public void onPlace(@NotNull HangingPlaceEvent event) {
        ItemStack item = event.getItemStack();
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        if (!meta.getPersistentDataContainer().has(InvisibleItemFrameRecipe.INVISIBLE_ITEM_FRAME_KEY, PersistentDataType.BYTE)) return;
        if (!(event.getEntity() instanceof ItemFrame frame)) return;

        frame.getPersistentDataContainer().set(
                InvisibleItemFrameRecipe.INVISIBLE_ITEM_FRAME_KEY,
                PersistentDataType.BYTE,
                (byte) 1
        );

        frame.setVisible(false);
    }

    @EventHandler
    public void onFrameDestroyEvent(@NotNull HangingBreakByEntityEvent event) {
        if(!(event.getEntity() instanceof ItemFrame frame)) return;
        if(!frame.getPersistentDataContainer().has(InvisibleItemFrameRecipe.INVISIBLE_ITEM_FRAME_KEY, PersistentDataType.BYTE)) return;

        event.setCancelled(true);
        frame.remove();

        if(event.getRemover() instanceof Player p && p.getGameMode() == GameMode.SURVIVAL)
            frame.getWorld().dropItemNaturally(frame.getLocation(), InvisibleItemFrameRecipe.getInvisibleItemFrameItem());
    }

}
