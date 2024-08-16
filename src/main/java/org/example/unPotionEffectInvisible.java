package org.example;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class unPotionEffectInvisible extends JavaPlugin implements Listener{

    @Override
    public void onEnable() {
        // Регистрируем обработчик событий
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPotionDrink(PlayerItemConsumeEvent event) {
        if (event.getItem().getType().toString().contains("POTION")) {
            Player player = event.getPlayer();

            // Задержка в 1 тик, чтобы эффект применился после питья зелья
            Bukkit.getScheduler().runTaskLater(this, () -> {
                // Проверяем активные эффекты игрока
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    if (effect.getType().equals(PotionEffectType.INVISIBILITY)) {
                        // Удаляем старый эффект
                        player.removePotionEffect(PotionEffectType.INVISIBILITY);

                        // Применяем новый эффект невидимости без частиц
                        player.addPotionEffect(new PotionEffect(
                                PotionEffectType.INVISIBILITY,
                                effect.getDuration(),
                                effect.getAmplifier(),
                                false, // ambient (не используется)
                                false  // particles (отключаем частицы)
                        ));
                    }
                }
            }, 1L); // Задержка в 1 тик
        }
    }
}
