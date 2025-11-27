package org.dev.nebula.core.menus.classic;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.dev.nebula.core.items.weapons.SimpleSword;
import org.dev.nebula.core.menus.MenuBase;

public class AdminSkillsMenu extends MenuBase {
    public AdminSkillsMenu(Player player) {
        super(player);
    }

    public Inventory getInventory() {
        Inventory inv = createInventory(this, 36, "Admin Menu");

        inv.setItem(13, new ItemStack(Material.DIAMOND));

        return inv;
    }

    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        if (event.getCurrentItem().getType() == Material.DIAMOND) {
            // player.getInventory().addItem(new SimpleSword());
            player.sendMessage("You clicked the diamond!");
            Bukkit.getLogger().info(player.getName() + " clicked the diamond in AdminMenu.");
        }
    }
}
