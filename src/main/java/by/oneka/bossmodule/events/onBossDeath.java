package by.oneka.bossmodule.events;

import by.oneka.bossmodule.entities.Boss;
import by.oneka.bossmodule.managers.BossManager;
import com.google.inject.Inject;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
public class onBossDeath implements Listener {
    @Inject
    BossManager bossesManager;

    public onBossDeath() {

    }

    @EventHandler
    public void onBossDeathHandler(EntityDeathEvent e) {
        if(bossesManager == null) return;

        Boss<? extends LivingEntity> boss = bossesManager.getBoss(e.getEntity().getUniqueId());
        if(boss == null) return;

        e.setDroppedExp(0);
        e.getDrops().clear();
    }
}

