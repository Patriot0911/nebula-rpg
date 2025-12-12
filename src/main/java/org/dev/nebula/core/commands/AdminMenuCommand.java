package org.dev.nebula.core.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.dev.nebula.core.items.weapons.SimpleSword;
import org.dev.nebula.core.menus.MenuBuilder;
import org.dev.nebula.core.services.ItemsService;

public class AdminMenuCommand implements CommandExecutor {

    public AdminMenuCommand() {
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

        ItemStack item = ItemsService.items.get(SimpleSword.ITEM_NAME).createItemStack(1);

        player.getInventory().addItem(item);
        openMenu(1, player);
        return true;
    }

    private void openMenu(int page, Player player) {
        ItemStack testItem = new ItemStack(Material.ACACIA_BOAT);
        MenuBuilder builder = MenuBuilder.create()
            .title("Перелік предметів")
            .size(6)
            .fillItems(1, 1, 3, 3, testItem, e -> { System.out.println("tested click");})
            .setItem(1, 1, new ItemStack(Material.ORANGE_BANNER), e -> {})
            .setItem(45, new ItemStack(Material.ORANGE_BANNER), e -> openMenu(page - 1, player))
            .setItem(53, new ItemStack(Material.GREEN_BANNER), e -> openMenu(page + 1, player))
            .setItem(49, new ItemStack(Material.RED_BANNER), e -> player.closeInventory());

        player.openInventory(builder.build(player));
    }
}
