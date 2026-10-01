package de.lalex.craftattack.invisibleItemFrame;

import de.lalex.craftattack.Main;
import de.lalex.craftattack.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public final class InvisibleItemFrameRecipe {

    private static final Main main = Main.getInstance();

    public static NamespacedKey INVISIBLE_ITEM_FRAME_KEY = new NamespacedKey(main, "invisible_item_frame");

    public static @NotNull ItemStack getInvisibleItemFrameItem() {
        ItemStack itemFrame = new ItemBuilder(Material.ITEM_FRAME)
                .setDisplayName("§dUnsichtbares Item Frame")
                .addEnchantment(Enchantment.INFINITY, 1)
                .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                .build();
        ItemMeta meta = itemFrame.getItemMeta();
        meta.getPersistentDataContainer().set(
                INVISIBLE_ITEM_FRAME_KEY,
                PersistentDataType.BYTE,
                (byte) 1
        );
        itemFrame.setItemMeta(meta);

        return itemFrame;
    }

    public static void registerInvisibleItemFrameRecipe() {
        ItemStack result = getInvisibleItemFrameItem();
        result.setAmount(2);

        NamespacedKey key = new NamespacedKey(main, "invisible_item_frame_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("GGG", "GFG", "GGG");
        recipe.setIngredient('G', Material.GLASS_PANE);
        recipe.setIngredient('F', Material.ITEM_FRAME);

        Bukkit.addRecipe(recipe);
    }

}
