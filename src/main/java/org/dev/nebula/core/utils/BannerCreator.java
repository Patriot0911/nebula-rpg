package org.dev.nebula.core.utils;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class BannerCreator {
    public static ItemStack bannerArrowRight(Material bannerMaterial, DyeColor bgColor, DyeColor hColor) {
        ItemStack banner = new ItemStack(bannerMaterial);
        BannerMeta meta = (BannerMeta) banner.getItemMeta();

        meta.addPattern(new Pattern(hColor, PatternType.RHOMBUS));
        meta.addPattern(new Pattern(hColor, PatternType.HALF_VERTICAL));
        meta.addPattern(new Pattern(bgColor, PatternType.SQUARE_BOTTOM_LEFT));
        meta.addPattern(new Pattern(bgColor, PatternType.SQUARE_TOP_LEFT));
        meta.addPattern(new Pattern(bgColor, PatternType.TRIANGLES_TOP));
        meta.addPattern(new Pattern(bgColor, PatternType.TRIANGLES_BOTTOM));
        meta.addPattern(new Pattern(hColor, PatternType.BORDER));

        banner.setItemMeta(meta);
        return banner;
    }

    public static ItemStack bannerArrowLeft(Material bannerMaterial, DyeColor bgColor, DyeColor hColor) {
        ItemStack banner = new ItemStack(bannerMaterial);
        BannerMeta meta = (BannerMeta) banner.getItemMeta();

        meta.addPattern(new Pattern(hColor, PatternType.RHOMBUS));
        meta.addPattern(new Pattern(hColor, PatternType.HALF_VERTICAL_RIGHT));
        meta.addPattern(new Pattern(bgColor, PatternType.SQUARE_BOTTOM_RIGHT));
        meta.addPattern(new Pattern(bgColor, PatternType.SQUARE_TOP_RIGHT));
        meta.addPattern(new Pattern(bgColor, PatternType.TRIANGLES_TOP));
        meta.addPattern(new Pattern(bgColor, PatternType.TRIANGLES_BOTTOM));
        meta.addPattern(new Pattern(hColor, PatternType.BORDER));

        banner.setItemMeta(meta);
        return banner;
    }

    public static ItemStack bannerCross(Material bannerMaterial, DyeColor hColor) {
        ItemStack banner = new ItemStack(bannerMaterial);
        BannerMeta meta = (BannerMeta) banner.getItemMeta();

        meta.addPattern(new Pattern(hColor, PatternType.CROSS));
        meta.addPattern(new Pattern(hColor, PatternType.BORDER));

        banner.setItemMeta(meta);
        return banner;
    }
}
