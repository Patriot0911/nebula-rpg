package org.dev.nebula.core.commands;

import java.util.ArrayList;
import java.util.List;

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

        openMenu(1, player);
        return true;
    }

    private void openMenu(int page, Player player) {
        List<ItemStack> itemList = new ArrayList<ItemStack>();
        for(int i = 0; i < page + 1; i++) {
            System.out.print(i);
            itemList.add(new ItemStack(Material.RABBIT));
        }
        MenuBuilder builder = MenuBuilder.create()
            .title("Перелік предметів")
            .size(6)
            .filler(new ItemStack(Material.GRAY_STAINED_GLASS))
            .displayArea(2, 2, 5, 5)
            .paginated(itemList)
            .page(page)
            .setItem(45, new ItemStack(Material.ORANGE_BANNER), e -> openMenu(page - 1, player))
            .setItem(53, new ItemStack(Material.GREEN_BANNER), e -> openMenu(page + 1, player))
            .setItem(49, new ItemStack(Material.RED_BANNER), e -> player.closeInventory());

        player.openInventory(builder.build(player));
    }
}
