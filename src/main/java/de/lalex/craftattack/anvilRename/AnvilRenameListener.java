package de.lalex.craftattack.anvilRename;

import de.lalex.craftattack.util.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public final class AnvilRenameListener implements Listener {

    @EventHandler
    public void onAnvilRename(@NotNull PrepareAnvilEvent event) {
        ItemStack result = event.getInventory().getResult();
        if (result == null || !result.hasItemMeta()) return;
        ItemMeta meta = result.getItemMeta();
        if (!meta.hasDisplayName()) return;

        Component originalName = meta.displayName();
        if (originalName == null) return;

        String legacyName = Message.SECTION.serialize(originalName);
        Component coloredName = Message.LEGACY.deserialize(legacyName).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);

        result.editMeta(m -> m.displayName(coloredName));
        event.setResult(result);
    }
}
