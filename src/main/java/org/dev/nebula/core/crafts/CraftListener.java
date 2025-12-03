package org.dev.nebula.core.crafts;

import org.bukkit.Keyed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.Recipe;

public class CraftListener implements Listener {
    private final CraftManager craftManager;

    public CraftListener(CraftManager craftManager) {
        this.craftManager = craftManager;
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (!(event.getWhoClicked() instanceof Player player))
            return;

        Recipe recipe = event.getRecipe();
        if (!(recipe instanceof Keyed keyed))
            return;

        CraftEntry entry = craftManager.getRecipes().values().stream()
                .filter(e -> e.key.equals(keyed.getKey()))
                .findFirst()
                .orElse(null);

        if (entry == null) return;

        for (CraftCondition cond : entry.conditions) {
            if (!cond.canCraft(player, event.getInventory())) {
                player.sendMessage("§c" + cond.getErrorMessage());
                event.setCancelled(true);
                return;
            }
        }
    }
}
