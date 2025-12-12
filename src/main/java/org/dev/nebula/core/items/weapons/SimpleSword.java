package org.dev.nebula.core.items.weapons;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.dev.nebula.core.crafts.CraftCondition;
import org.dev.nebula.core.crafts.conditions.SkillRequirement;
import org.dev.nebula.core.db.models.SkillData;
import org.dev.nebula.core.events.EventBus;
import org.dev.nebula.core.events.busEvents.items.PlayerInteractBusEvent;
import org.dev.nebula.core.items.ItemBase;
import org.dev.nebula.core.services.UsersService;
import org.dev.nebula.core.skills.passive.LifeStealPassive;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class SimpleSword extends ItemBase {
    public static final String ITEM_NAME = "simple_sword";

    public SimpleSword(EventBus bus) {
        super(bus);
        bus.subscribe(PlayerInteractBusEvent.class, this::onPlayerInteract);
    }

    @Override
    public String getItemName() {
        return "item.simple_sword_01.name";
    }
    @Override
    public String getItemDescription() {
        return "item.simple_sword_01.description";
    }

    @Override
    public String getItemKeyName() {
        return SimpleSword.ITEM_NAME;
    }

    @Override
    public String[] getCraftShape() {
        return new String[] {
            " S ",
            " D ",
            "D  "
        };
    }
    @Override
    public Map<Character, ItemStack> getCraftMapping() {
        return Map.of(
            'S', new ItemStack(Material.STONE),
            'D', new ItemStack(Material.DIRT)
        );
    }
    @Override
    public CraftCondition[] getCraftConditions() {
        return new CraftCondition[] {
            new SkillRequirement(LifeStealPassive.SKILL_NAME, 0)
        };
    }

    @Override
    public ItemStack createItemStack(Integer count) {
        ItemStack item = new ItemStack(Material.NETHER_BRICK, count == null ? 1 : count);
        ItemMeta meta = item.getItemMeta();

        List<Component> loreLines = List.of(
            Component.text(getItemDescription()+".1"),
            Component.text(getItemDescription()+".2")
        );

        meta.displayName(
            Component
                .text(getItemName())
                .color(TextColor.color(100, 100, 255))
        );
        meta.lore(loreLines);

        meta.addItemFlags(
            ItemFlag.HIDE_ATTRIBUTES,
            ItemFlag.HIDE_UNBREAKABLE,
            ItemFlag.HIDE_ENCHANTS
        );

        setItemTagName(meta);

        item.setItemMeta(meta);
        return item;
    }

    public void onPlayerInteract(PlayerInteractBusEvent e) {
        ItemStack itemStack = e.event.getPlayer().getInventory().getItemInMainHand();
        if (itemStack == null || itemStack.getType().isAir()) return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return;
        if (!isSameItem(itemMeta)) return;
        Player player = e.event.getPlayer();
        int itemSlot = player.getInventory().getHeldItemSlot();
        player.getInventory().setItem(itemSlot, null);

        UUID skillUuid = UUID.nameUUIDFromBytes(LifeStealPassive.SKILL_NAME.getBytes());
        UsersService.getUserData(player.getUniqueId()).addSkill(
            new SkillData(UUID.randomUUID(), skillUuid, LifeStealPassive.SKILL_NAME, 1, null)
        );
        System.out.println(SimpleSword.ITEM_NAME);
    }
}
