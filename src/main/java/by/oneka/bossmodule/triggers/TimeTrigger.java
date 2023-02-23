package by.oneka.bossmodule.triggers;

import by.oneka.bossmodule.entities.Boss;
import com.google.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeTrigger extends Trigger {

    Plugin plugin;

    Boss<? extends LivingEntity> boss;

    long period;
    int taskId;

    public TimeTrigger(Boss<? extends LivingEntity> boss, long period) {
        this.boss = boss;
        this.period = period;

    }

    @Override
    public void resetTriggered() {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> trigger(boss), period, period);
    }
    public void stopScheduler() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    @Override
    public void trigger(Boss<? extends LivingEntity> boss) {
        boss.getSkills(this).forEach(skill -> skill.execute(boss));
    }
}
