package org.dev.nebula.core.items.shards;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.dev.nebula.core.events.EventBus;
import org.dev.nebula.core.items.ItemBase;
import org.dev.nebula.core.services.ItemsService;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class IceCrystal extends ItemBase {
    public static final String ITEM_NAME = "ice_crystal";

    public IceCrystal(EventBus bus) {
        super(bus);
    }

    @Override
    public String getItemName() {
        return "Ice crystal";
    }
    @Override
    public String getItemDescription() {
        return null;
    }

    @Override
    public String getItemKeyName() {
        return IceCrystal.ITEM_NAME;
    }

    @Override
    public String[] getCraftShape() {
        return new String[] {
            " S ",
            "S S",
            " S "
        };
    }
    @Override
    public Map<Character, ItemStack> getCraftMapping() {
        return Map.of(
            'S', ItemsService.getItem(IceShard.ITEM_NAME).createItemStack(1)
        );
    }

    @Override
    public ItemStack createItemStack(Integer count) {
        ItemStack item = new ItemStack(Material.PRISMARINE_CRYSTALS, count == null ? 1 : count);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(
            Component
                .text(getItemName())
                .color(TextColor.color(150, 150, 255))
        );
        meta.addEnchant(Enchantment.DENSITY, 1, true);

        meta.addItemFlags(
            ItemFlag.HIDE_ATTRIBUTES,
            ItemFlag.HIDE_UNBREAKABLE,
            ItemFlag.HIDE_ENCHANTS
        );

        setItemTagName(meta);

        item.setItemMeta(meta);
        return item;
    }
}
