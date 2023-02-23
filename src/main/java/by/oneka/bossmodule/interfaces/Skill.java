package by.oneka.bossmodule.interfaces;

import by.oneka.bossmodule.entities.Boss;
import org.bukkit.entity.LivingEntity;

public interface Skill {

    void execute(Boss<? extends LivingEntity> boss);
}
