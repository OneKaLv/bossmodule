package by.oneka.bossmodule.triggers;

import by.oneka.bossmodule.entities.Boss;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class DamageTrigger extends Trigger {
    @Getter final EntityDamageEvent.DamageCause type;

    final long delay;
    long triggerAgainAt;

    public DamageTrigger(EntityDamageEvent.DamageCause type, long delay) {
        this.type = type;
        this.delay = delay;

        triggerAgainAt = System.currentTimeMillis();
    }

    @Override
    public void trigger(Boss<? extends LivingEntity> boss) {
        if(System.currentTimeMillis() < triggerAgainAt) return;

        boss.getSkills(this).forEach(skill -> skill.execute(boss));

        triggerAgainAt = System.currentTimeMillis() + delay;
    }
}
