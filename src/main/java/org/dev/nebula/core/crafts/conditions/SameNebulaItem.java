package org.dev.nebula.core.crafts.conditions;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import org.dev.nebula.core.crafts.CraftCondition;
import org.dev.nebula.core.items.ItemBase;
import org.dev.nebula.core.services.UsersService;

public class SameNebulaItem extends CraftCondition {

    private final Map<Character, ItemStack> mapping;
    private final String[] shape;

    public SameNebulaItem(UsersService userService, String[] shape, Map<Character, ItemStack> mapping) {
        super(userService);
        this.shape = shape;
        this.mapping = mapping;
    }

    @Override
    public boolean canCraft(Player player, CraftingInventory inv) {
        String[] nShape = normalizeShape(shape);

        ItemStack[][] nMatrix = normalizeMatrix(inv.getMatrix());

        if (nShape.length != nMatrix.length) return false;
        if (nShape[0].length() != nMatrix[0].length) return false;

        for (int y = 0; y < nShape.length; y++) {
            for (int x = 0; x < nShape[y].length(); x++) {

                char symbol = nShape[y].charAt(x);
                ItemStack given = nMatrix[y][x];

                if (symbol == ' ') {
                    if (given != null && !given.isEmpty()) return false;
                    continue;
                }

                ItemStack required = mapping.get(symbol);
                if (!matchesCustomItem(given, required)) return false;
            }
        }

        return true;
    }

    private String[] normalizeShape(String[] shape) {
        int rows = shape.length;
        int cols = shape[0].length();

        int minRow = rows, maxRow = -1;
        int minCol = cols, maxCol = -1;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (shape[r].charAt(c) != ' ') {
                    if (r < minRow) minRow = r;
                    if (r > maxRow) maxRow = r;
                    if (c < minCol) minCol = c;
                    if (c > maxCol) maxCol = c;
                }
            }
        }

        if (maxRow == -1) return new String[0];

        int newH = maxRow - minRow + 1;
        int newW = maxCol - minCol + 1;

        String[] out = new String[newH];
        for (int r = 0; r < newH; r++) {
            out[r] = shape[minRow + r].substring(minCol, minCol + newW);
        }

        return out;
    }

    public ItemStack[][] normalizeMatrix(ItemStack[] matrix) {
        int minRow = 3, maxRow = -1;
        int minCol = 3, maxCol = -1;

        for (int i = 0; i < 9; i++) {
            ItemStack item = matrix[i];
            if (item == null || item.isEmpty()) continue;

            int r = i / 3;
            int c = i % 3;

            if (r < minRow) minRow = r;
            if (r > maxRow) maxRow = r;
            if (c < minCol) minCol = c;
            if (c > maxCol) maxCol = c;
        }

        if (maxRow == -1) return new ItemStack[0][0];

        int height = maxRow - minRow + 1;
        int width  = maxCol - minCol + 1;

        ItemStack[][] out = new ItemStack[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                out[y][x] = matrix[(minRow + y) * 3 + (minCol + x)];
            }
        }

        return out;
    }


    // public boolean canCraft(Player player, CraftingInventory inv) {
    //     ItemStack[] matrix = inv.getMatrix();

    //     for (int slot = 0; slot < matrix.length; slot++) {
    //         ItemStack given = matrix[slot];

    //         int row = slot / 3;
    //         int col = slot % 3;

    //         char symbol = shape[row].charAt(col);

    //         if (symbol == ' ') {
    //             if (given != null && !given.isEmpty()) return false;
    //             continue;
    //         }

    //         ItemStack required = mapping.get(symbol);

    //         if (!matchesCustomItem(given, required)) return false;
    //     }

    //     return true;
    // }

    private boolean matchesCustomItem(ItemStack given, ItemStack required) {
        if (given == null || given.isEmpty()) return false;

        if (given.getType() != required.getType()) return false;

        var gMeta = given.getItemMeta();
        var rMeta = required.getItemMeta();

        PersistentDataContainer gPdc = gMeta.getPersistentDataContainer();
        PersistentDataContainer rPdc = rMeta.getPersistentDataContainer();
        if (gPdc == null || rPdc == null) return false;

        String requiredValue = rPdc.get(ItemBase.ITEM_ID_KEY_NAMESPACED_KEY, PersistentDataType.STRING);
        String givenValue = gPdc.get(ItemBase.ITEM_ID_KEY_NAMESPACED_KEY, PersistentDataType.STRING);

        return requiredValue.equals(givenValue);
    }
}
