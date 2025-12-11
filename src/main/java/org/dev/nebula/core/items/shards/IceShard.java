package org.dev.nebula.core.items.shards;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.dev.nebula.core.events.EventBus;
import org.dev.nebula.core.items.ItemBase;
import org.dev.nebula.core.services.ItemsService;
import org.dev.nebula.core.services.UsersService;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class IceShard extends ItemBase {
    public static final String ITEM_NAME = "ice_shard";

    public IceShard(EventBus bus, UsersService userService, ItemsService itemsService) {
        super(userService, itemsService);
    }

    @Override
    public String getItemName() {
        return "Ice Shard";
    }
    @Override
    public String getItemDescription() {
        return null;
    }

    @Override
    public String getItemKeyName() {
        return IceShard.ITEM_NAME;
    }

    @Override
    public ItemStack createItemStack(Integer count) {
        ItemStack item = new ItemStack(Material.PRISMARINE_SHARD, count == null ? 1 : count);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(
            Component
                .text(getItemName())
                .color(TextColor.color(150, 150, 255))
        );

        setItemTagName(meta);

        item.setItemMeta(meta);
        return item;
    }
}
