package by.oneka.bossmodule.events;

import by.oneka.bossmodule.entities.Boss;
import by.oneka.bossmodule.managers.BossManager;
import by.oneka.bossmodule.triggers.DamageTrigger;
import by.oneka.bossmodule.triggers.HealthTrigger;
import com.google.inject.Inject;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class onBossDamage implements Listener {
    @Inject
    BossManager bossesManager;

    public onBossDamage() {

    }

    @EventHandler
    public void onBossDamageHandler(EntityDamageEvent e) {
        if(bossesManager == null) return;

        Boss<? extends LivingEntity> boss = bossesManager.getBoss(e.getEntity().getUniqueId());
        if(boss == null) return;

        boss.getSkills().keySet().stream()
                .filter(trigger -> trigger instanceof DamageTrigger && e.getCause() == ((DamageTrigger) trigger).getType())
                .forEach(trigger -> trigger.trigger(boss));

        boss.getSkills().keySet().stream()
                .filter(trigger -> trigger instanceof HealthTrigger && ((HealthTrigger) trigger).getHealth() <= boss.getEntity().getEntity().getHealth() - e.getFinalDamage())
                .forEach(trigger -> trigger.trigger(boss));

        if(!(e instanceof EntityDamageByEntityEvent)) return;


        Entity damager = ((EntityDamageByEntityEvent) e).getDamager();
        if(!(damager instanceof Player)) return;
    }
}

