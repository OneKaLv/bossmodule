package by.oneka.bossmodule.entities;

import by.oneka.bossmodule.interfaces.Skill;
import by.oneka.bossmodule.triggers.Trigger;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import java.util.*;

public abstract class Boss<Entity extends LivingEntity> {

    @Getter protected Plugin plugin;
    @Getter
    CustomEntity<Entity> entity;
    @Getter
    final HashMap<Trigger, List<Skill>> skills = new HashMap<>();
    Location spawnLocation;
    @Getter
    int timeUntilRespawn = 0;
    int respawnDelay;
    List<EntityDamageEvent.DamageCause> protects = new ArrayList<>();

    public List<Skill> getSkills(Trigger trigger) {
        return skills.get(trigger);
    }

    public double getHealth() {
        return entity.getEntity().getHealth();
    }

    public String getName() {
        return entity.getEntity().getCustomName();
    }

    public class Builder {
        public Builder(CustomEntity<Entity> entity) {
            Boss.this.entity = entity;
        }

        public Builder setSpawnLocation(World world, double x, double y, double z) throws Exception {
            if (world == null) throw new Exception("Мир не может быть null");
            spawnLocation = new Location(world, x, y, z);
            return this;
        }


        public Builder setRespawnDelay(int time) throws Exception {
            if (time < 1) throw new Exception("Время должно быть больше 0");
            respawnDelay = time;
            return this;
        }

        public Builder addProtects(EntityDamageEvent.DamageCause... protects) throws Exception {
            for (EntityDamageEvent.DamageCause protect : protects) {
                if (protect == null) throw new Exception("Защита не может быть null");
                if (!Boss.this.protects.contains(protect)) Boss.this.protects.add(protect);
            }

            return this;
        }

        public Builder addSkills(Trigger trigger, Skill... skills) throws Exception {
            if (trigger == null) throw new Exception("Тригер не может быть null");

            if (Boss.this.skills.containsKey(trigger))
                for (Skill skill : skills) {
                    if (skill == null) throw new Exception("Скилл не может быть null");
                    Boss.this.skills.get(trigger).add(skill);
                }
            else
                Boss.this.skills.put(trigger, Arrays.asList(skills));
            return this;
        }

        public Boss<Entity> build() throws Exception {
            if (spawnLocation.getWorld() == null) throw new Exception("Локация спавна не установлена");

            return Boss.this;
        }
    }
}
