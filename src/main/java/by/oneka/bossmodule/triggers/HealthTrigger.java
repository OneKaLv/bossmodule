package by.oneka.bossmodule.triggers;

import by.oneka.bossmodule.entities.Boss;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.entity.LivingEntity;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class HealthTrigger extends Trigger {
    @Getter final double health;

    public HealthTrigger(double health) {
        this.health = health;
    }

    @Override
    public void trigger(Boss<? extends LivingEntity> boss) {
        if(triggered) return;
        if(boss.getHealth() > health) return;

        triggered = true;

        boss.getSkills(this).forEach(skill -> skill.execute(boss));
    }
}
