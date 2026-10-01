package de.lalex.craftattack.util;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private ItemMeta itemMeta;
    private ItemStack itemStack;

    public ItemBuilder(Material mat) {
        itemStack = new ItemStack(mat);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setDisplayName(String name) {
        itemMeta.displayName(LegacyComponentSerializer.legacySection().deserialize(name));
        return this;
    }

    public ItemBuilder setLore(String... s) {
        List<TextComponent> lore = Arrays.stream(s)
                .map(line -> LegacyComponentSerializer.legacySection().deserialize(line))
                .toList();
        itemMeta.lore(lore);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean u) {
        itemMeta.setUnbreakable(u);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... s) {
        itemMeta.addItemFlags(s);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment e, int level) {
        itemMeta.addEnchant(e, level, true);
        return this;
    }

    @Override
    public String toString() {
        return "ItemBuilder{" +
                "itemMeta=" + itemMeta +
                ", itemStack=" + itemStack +
                '}';
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}