package org.dev.nebula.core.items.armor;

import java.util.Map;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.dev.nebula.core.crafts.CraftCondition;
import org.dev.nebula.core.crafts.conditions.SameNebulaItem;
import org.dev.nebula.core.events.EventBus;
import org.dev.nebula.core.items.ItemBase;
import org.dev.nebula.core.items.shards.IceShard;
import org.dev.nebula.core.services.ItemsService;
import org.dev.nebula.core.services.UsersService;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class IceLeggings extends ItemBase {
    public static final String ITEM_NAME = "ice_armor_leggings";

    public IceLeggings(EventBus bus, UsersService userService, ItemsService itemsService) {
        super(userService, itemsService);
    }

    @Override
    public String getItemName() {
        return "Ice Leggings";
    }
    @Override
    public String getItemDescription() {
        return null;
    }

    @Override
    public String getItemKeyName() {
        return IceLeggings.ITEM_NAME;
    }

    @Override
    public CraftCondition[] getCraftConditions() {
        return new CraftCondition[]{
            new SameNebulaItem(
                userService,
                getCraftShape(),
                getCraftMapping()
            )
        };
    }
    @Override
    public String[] getCraftShape() {
        return new String[] {
            "III",
            "I I",
            "I I"
        };
    }
    @Override
    public Map<Character, ItemStack> getCraftMapping() {
        return Map.of(
            'I', itemsService.getItems().get(IceShard.ITEM_NAME).createItemStack(1)
        );
    }

    @Override
    public ItemStack createItemStack(Integer count) {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, count == null ? 1 : count);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(
            Component
                .text(getItemName())
                .color(TextColor.color(175, 175, 255))
        );
        ((LeatherArmorMeta) meta).setColor(Color.AQUA);

        AttributeModifier speedModifier = new AttributeModifier(
            new NamespacedKey("nebula", ITEM_NAME+"speed_mod_leggings"),
            0.07,
            Operation.ADD_NUMBER
        );
        AttributeModifier armorModifier = new AttributeModifier(
            new NamespacedKey("nebula", ITEM_NAME+"armor_mod_leggings"),
            2,
            Operation.ADD_NUMBER
        );

        meta.addAttributeModifier(
            Attribute.MOVEMENT_SPEED,
            speedModifier
        );
        meta.addAttributeModifier(
            Attribute.ARMOR_TOUGHNESS,
            armorModifier
        );

        setItemTagName(meta);

        item.setItemMeta(meta);
        return item;
    }
}
