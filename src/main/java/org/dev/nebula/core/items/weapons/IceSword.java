package org.dev.nebula.core.items.weapons;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.dev.nebula.core.events.EventBus;
import org.dev.nebula.core.events.busEvents.damage.DamageGiveEvent;
import org.dev.nebula.core.events.busEvents.items.ItemBreakBusEvent;
import org.dev.nebula.core.items.ItemBase;
import org.dev.nebula.core.items.shards.IceShard;
import org.dev.nebula.core.services.ItemsService;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class IceSword extends ItemBase {
    public static final String ITEM_NAME = "ice_sword";

    public IceSword(EventBus bus) {
        super(bus);
        bus.subscribe(ItemBreakBusEvent.class, this::onBreak);
        bus.subscribe(DamageGiveEvent.class, this::onPlayerAttack);
    }

    @Override
    public String getItemName() {
        return "Ice Blade";
    }
    @Override
    public String getItemDescription() {
        return null;
    }

    @Override
    public String getItemKeyName() {
        return IceSword.ITEM_NAME;
    }

    @Override
    public String[] getCraftShape() {
        return new String[] {
            " I ",
            " I ",
            " S "
        };
    }
    @Override
    public Map<Character, ItemStack> getCraftMapping() {
        return Map.of(
            'I', new ItemStack(Material.PACKED_ICE),
            'S', new ItemStack(Material.STICK)
        );
    }

    @Override
    public ItemStack createItemStack(Integer count) {
        ItemStack item = new ItemStack(Material.IRON_SWORD, count == null ? 1 : count);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(
            Component
                .text(getItemName())
                .color(TextColor.color(150, 150, 255))
        );

        AttributeModifier damageModifier = new AttributeModifier(
            new NamespacedKey("nebula", ITEM_NAME+"damage_mod"),
            3.0,
            Operation.ADD_NUMBER
        );
        AttributeModifier attackSpeedModifier = new AttributeModifier(
            new NamespacedKey("nebula", ITEM_NAME+"attack_speed_mod"),
            2.8,
            Operation.ADD_NUMBER
        );

        meta.addAttributeModifier(
            Attribute.ATTACK_DAMAGE,
            damageModifier
        );
        meta.addAttributeModifier(
            Attribute.ATTACK_SPEED,
            attackSpeedModifier
        );

        setItemTagName(meta);

        item.setItemMeta(meta);
        return item;
    }

    public void onPlayerAttack(DamageGiveEvent e) {
        ItemStack itemStack = e.getProducer().getInventory().getItemInMainHand();
        if (itemStack == null || itemStack.getType().isAir()) return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return;
        if (!isSameItem(itemMeta)) return;

        int current = e.target.getFreezeTicks();
        int max = e.target.getMaxFreezeTicks();
        int added = 20;

        int newFreeze = Math.min(current + added, max);

        e.target.setFreezeTicks(newFreeze);
    }
    public void onBreak(ItemBreakBusEvent e) {
        ItemStack itemStack = e.event.getBrokenItem();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return;
        if (!isSameItem(itemMeta)) return;

        e.event.getPlayer().getInventory().addItem(
            ItemsService.getItem(IceShard.ITEM_NAME).createItemStack(
                1 + (int) (Math.random() * 2)
            )
        );
    }
}
