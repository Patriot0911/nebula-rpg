package org.dev.nebula.core.spawn.mobs.frozen;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.dev.nebula.core.items.weapons.IceSword;
import org.dev.nebula.core.services.ItemsService;
import org.dev.nebula.core.services.MobsService;
import org.dev.nebula.core.spawn.interfaces.CustomMob;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class FrozenKnight extends CustomMob {
    public static final String MOB_ID = "frozen_knight";

    @Override
    public EntityType getEntityType() {
        return EntityType.ZOMBIE;
    }

    @Override
    public void modifyEntity(LivingEntity entity) {
        Zombie zombie = (Zombie) entity;
        zombie.customName(
            Component.text("Frozen Knight").color(TextColor.color(100, 105, 240))
        );
        zombie.setCustomNameVisible(true);
        zombie.setAdult();
        zombie.setCanPickupItems(true);

        zombie.getEquipment().setHelmet(new ItemStack(Material.ICE));
        zombie.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
        zombie.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        zombie.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));

        zombie.getEquipment().setItemInMainHand(ItemsService.getItem(IceSword.ITEM_NAME).createItemStack(1));

        if (zombie.getPersistentDataContainer() != null) {
            zombie.getPersistentDataContainer().set(MobsService.MOB_ID, PersistentDataType.STRING, MOB_ID);
        }

        AttributeInstance maxHealth = zombie.getAttribute(Attribute.MAX_HEALTH);
        AttributeInstance attack = zombie.getAttribute(Attribute.ATTACK_DAMAGE);
        AttributeInstance speed = zombie.getAttribute(Attribute.MOVEMENT_SPEED);
        if (attack != null) {
            attack.setBaseValue(12.0);
        }
        if (maxHealth != null) {
            maxHealth.setBaseValue(50.0);
            zombie.setHealth(50.0);
        }
        if (speed != null) {
            speed.setBaseValue(0.3);
        }
    }
}

