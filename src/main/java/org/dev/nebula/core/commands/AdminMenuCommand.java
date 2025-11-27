package org.dev.nebula.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.dev.nebula.core.items.weapons.SimpleSword;
import org.dev.nebula.core.menus.classic.AdminSkillsMenu;
import org.dev.nebula.core.services.ItemsService;

public class AdminMenuCommand implements CommandExecutor {
    private ItemsService itemsService;

    public AdminMenuCommand(ItemsService itemsService) {
        this.itemsService = itemsService;
    };

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can open menus.");
            return true;
        }
        if (!sender.hasPermission("nebula.admin")) {
            sender.sendMessage("You don't have permission!");
            return true;
        }

        ItemStack item = itemsService.getItems().get(SimpleSword.ITEM_NAME).createItemStack();

        player.getInventory().addItem(item);

        new AdminSkillsMenu(player).open();
        return true;
    }
}
