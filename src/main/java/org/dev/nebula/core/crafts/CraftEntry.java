package org.dev.nebula.core.crafts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.NamespacedKey;

public class CraftEntry {
    public NamespacedKey key;
    public List<CraftCondition> conditions = new ArrayList<CraftCondition>();
}
