package org.dev.nebula.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dev.nebula.core.menus.classic.AdminSkillsMenu;

public class AdminMenuCommand implements CommandExecutor {
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

        new AdminSkillsMenu(player).open();
        return true;
    }
}
