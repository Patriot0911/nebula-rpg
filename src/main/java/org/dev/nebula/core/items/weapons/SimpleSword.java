package org.dev.nebula.core.items.weapons;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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
import org.dev.nebula.core.items.ItemManager;
import org.dev.nebula.core.services.UsersService;
import org.dev.nebula.core.skills.passive.LifeStealPassive;

import net.kyori.adventure.text.format.NamedTextColor;

public class SimpleSword extends ItemBase {
    public static final String ITEM_NAME = "simple_sword";

    public SimpleSword(EventBus bus, UsersService userService) {
        super(userService);
        bus.subscribe(PlayerInteractBusEvent.class, this::onPlayerInteract);
    }

    @Override
    public String getItemName() {
        return "Test Name";
    }
    @Override
    public String getItemDescription() {
        return "Test Description";
    }

    @Override
    public NamespacedKey getItemTag() {
        return new NamespacedKey(ItemManager.WeaponNameSpace, SimpleSword.ITEM_NAME);
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
            new SkillRequirement(userService, LifeStealPassive.SKILL_NAME, 0)
        };
    }

    @Override
    public ItemStack createItemStack(Integer count) {
        ItemStack item = new ItemStack(Material.NETHER_BRICK, count == null ? 1 : count);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(NamedTextColor.AQUA + getItemName());
        meta.setLore(List.of(getItemDescription()));

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
        userService.getUserData(player.getUniqueId()).addSkill(
            new SkillData(UUID.randomUUID(), skillUuid, LifeStealPassive.SKILL_NAME, 1, null)
        );
        System.out.println(SimpleSword.ITEM_NAME);
    }
}
