package org.dev.nebula.core.achievements.implementation;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;
import org.dev.nebula.core.achievements.AchievementBase;
import org.dev.nebula.core.events.EventBus;
import org.dev.nebula.core.events.busEvents.PotionEffectEvent;
import org.dev.nebula.core.events.busEvents.items.PlayerItemConsumeBusEvent;
import org.dev.nebula.core.services.UsersService;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class RottenFood extends AchievementBase {
    public static final String KEY = "rotten_food";
    public static final int GOAL = 20;

    private final int IMUNE_PROGRESS_POINT = 5;

    public RottenFood(EventBus eventBus, UsersService usersService) {
        super(eventBus, usersService);

        eventBus.subscribe(PlayerItemConsumeBusEvent.class, this::onPlayerConsume);
        eventBus.subscribe(PotionEffectEvent.class, this::onEffect);
    }

    public void onPlayerConsume(PlayerItemConsumeBusEvent e) {
        UUID playerId = e.event.getPlayer().getUniqueId();
        if (e.event.getItem().getType() == Material.ROTTEN_FLESH) {
            int progressCount = getAchievementUserProgress(playerId);
            if (!isAchieved(playerId)) {
                addAchievementProgress(playerId, 1);
                progressCount += 1;
                e.event.getPlayer().sendActionBar(
                    Component.text("Progress: " + progressCount).color(TextColor.color(255, 20, 150))
                );
            }
        }
    }

    @EventHandler
    public void onEffect(PotionEffectEvent e) {
        if (!(e.event.getEntity() instanceof Player player)) return;
        if (e.event.getNewEffect() == null) return;

        if (
            e.event.getNewEffect().getType() == PotionEffectType.HUNGER &&
            e.event.getCause() == EntityPotionEffectEvent.Cause.FOOD
        ) {
            UUID playerId = player.getUniqueId();
            int progressCount = getAchievementUserProgress(playerId);
            if (progressCount >= IMUNE_PROGRESS_POINT) {
                e.event.setCancelled(true);
                player.sendActionBar(
                    Component.text("No effects!").color(TextColor.color(255, 20, 150))
                );
            }
        }
    }

    @Override
    public int getGoal() {
        return RottenFood.GOAL;
    }
    @Override
    public String getKey() {
        return RottenFood.KEY;
    }
}
