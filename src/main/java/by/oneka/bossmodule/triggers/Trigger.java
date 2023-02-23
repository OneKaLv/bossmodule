package by.oneka.bossmodule.triggers;

import by.oneka.bossmodule.entities.Boss;
import org.bukkit.entity.LivingEntity;

public abstract class Trigger {
    protected boolean triggered = false;
    public void resetTriggered() {
        triggered = false;
    }
    public abstract void trigger(Boss<? extends LivingEntity> boss);
}
