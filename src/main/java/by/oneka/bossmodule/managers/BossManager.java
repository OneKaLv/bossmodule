package by.oneka.bossmodule.managers;

import by.oneka.bossmodule.entities.Boss;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.entity.LivingEntity;

import java.util.*;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class BossManager {
    @Getter
    List<Boss<? extends LivingEntity>> bosses = new ArrayList<>();

    public void removeBosses() {
        bosses.forEach(boss -> boss.getEntity().getEntity().remove());
    }

    public void addBoss(Boss<? extends LivingEntity> boss) {
        bosses.add(boss);
    }

    public Boss<? extends LivingEntity> getBoss(UUID id) {
        return bosses.stream().filter(boss -> boss.getEntity() != null && boss.getEntity().getEntity() != null && boss.getEntity().getEntity().getUniqueId() == id).findFirst().orElse(null);
    }
}
