package org.dev.nebula.core.spawn.mobs.frozen;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.dev.nebula.core.spawn.interfaces.CustomMob;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class MahahaZombie implements CustomMob {
    @Override
    public EntityType getEntityType() {
        return EntityType.ZOMBIE;
    }

    @Override
    public void modifyEntity(LivingEntity entity) {
        Zombie zombie = (Zombie) entity;
        zombie.customName(
            Component.text("Mahaha").color(TextColor.color(20, 25, 240))
        );
        zombie.setCustomNameVisible(true);
        zombie.setAdult();
        zombie.setCanPickupItems(true);
        zombie.getEquipment().setHelmet(new ItemStack(Material.ICE));
        zombie.getEquipment().setHelmetDropChance(0f);

        AttributeInstance maxHealth = zombie.getAttribute(Attribute.MAX_HEALTH);
        AttributeInstance attack = zombie.getAttribute(Attribute.ATTACK_DAMAGE);
        if (attack != null) {
            attack.setBaseValue(10.0);
        }
        if (maxHealth != null) {
            maxHealth.setBaseValue(60.0);
            zombie.setHealth(60.0);
        }
    }
}

