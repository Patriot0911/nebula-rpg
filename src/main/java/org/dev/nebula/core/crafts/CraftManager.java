package org.dev.nebula.core.crafts;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.dev.nebula.NebulaPlugin;
import org.dev.nebula.core.items.ItemBase;
import org.dev.nebula.core.services.ItemsService;

public class CraftManager {
    private final NebulaPlugin plugin;
    private final Map<String, CraftEntry> recipes = new HashMap<>();

    public CraftManager(NebulaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(
            new CraftListener(this), plugin
        );
    }

    public void registerShapedCraft(String itemKey, ItemStack result, String[] shape, Map<Character, ItemStack> mapping, CraftCondition... conditions) {
        NamespacedKey key = new NamespacedKey(plugin, itemKey+"_craft");
        ShapedRecipe rec = new ShapedRecipe(key, result);
        rec.shape(shape);

        for (var e : mapping.entrySet()) {
            rec.setIngredient(e.getKey(), e.getValue().getType());
        }

        Bukkit.addRecipe(rec);

        CraftEntry entry = new CraftEntry();
        entry.key = key;
        entry.conditions.addAll(Arrays.asList(conditions));

        recipes.put(itemKey, entry);
    }

    public void registerShapelessCraft(String id, ItemStack result, List<ItemStack> ingredients, CraftCondition... conditions) {
        NamespacedKey key = new NamespacedKey(plugin, id);
        ShapelessRecipe rec = new ShapelessRecipe(key, result);

        for (var item : ingredients) {
            rec.addIngredient(item.getType());
        }

        Bukkit.addRecipe(rec);

        CraftEntry entry = new CraftEntry();
        entry.key = key;
        entry.conditions.addAll(Arrays.asList());

        recipes.put(id, entry);
    }

    public Map<String, CraftEntry> getRecipes() {
        return recipes;
    }

    public void registerCrafts() {
        for (Entry<String, ItemBase> e : ItemsService.items.entrySet()) {
            ItemBase item = e.getValue();
            if (item.getCraftMapping() == null || (item.getCraftShape() == null && item.getCraftShapeLess() == null))
                continue;
            if (item.getCraftShapeLess() != null) {
                registerShapelessCraft(
                    item.getItemKeyName(),
                    item.createItemStack(1),
                    item.getCraftShapeLess(),
                    item.getCraftConditions()
                );
            }
            if (item.getCraftShape() != null) {
                System.out.println("shape craft");
                registerShapedCraft(
                    item.getItemKeyName(),
                    item.createItemStack(1),
                    item.getCraftShape(),
                    item.getCraftMapping(),
                    item.getCraftConditions()
                );
            }
        }
    }
}
