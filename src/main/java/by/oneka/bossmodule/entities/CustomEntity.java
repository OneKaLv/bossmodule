package by.oneka.bossmodule.entities;

import com.google.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.BiConsumer;


@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class CustomEntity<Entity extends LivingEntity> implements Listener {

    Plugin plugin;
    @Getter
    final Entity entity;

    final BiConsumer<EntityDeathEvent, CustomEntity<Entity>> entityDeathEvent;

    public CustomEntity(@NonNull Class<Entity> entityClass, @NonNull Location location, @Nullable String customName,
                        double maxHealth, @Nullable EntityEquipment equipment, BiConsumer<EntityDeathEvent, CustomEntity<Entity>> entityDeathEvent) {
        EntityType entityType = null;
        for (EntityType type : EntityType.values()) {
            assert type.getEntityClass() != null;
            if (!type.getEntityClass().getName().equals(entityClass.getName())) continue;

            entityType = type;
            break;
        }
        this.entity = (Entity) location.getWorld().spawnEntity(location, entityType);

        if (customName != null) {
            entity.setCustomName(customName);
            entity.setCustomNameVisible(true);
        }

        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(maxHealth);
        entity.setHealth(maxHealth);

        EntityEquipment entityEquipment = entity.getEquipment();

        if (entityEquipment != null && equipment != null) {
            entityEquipment.setHelmet(equipment.getHelmet());
            entityEquipment.setChestplate(equipment.getChestplate());
            entityEquipment.setLeggings(equipment.getLeggings());
            entityEquipment.setBoots(equipment.getBoots());
            entityEquipment.setItemInMainHand(equipment.getItemInMainHand());
            entityEquipment.setItemInOffHand(equipment.getItemInOffHand());
        }

        this.entityDeathEvent = entityDeathEvent;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public CustomEntity<Entity> setTarget(LivingEntity entity) {
        if (entity instanceof Mob)
            ((Mob) this.entity).setTarget(entity);

        return this;
    }

    @EventHandler
    public void onCustomEntityDamaged(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        if (!e.getEntity().equals(entity)) return;

        ((Player) e.getDamager()).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(entity.getHealth() + "❤"));
    }

    @EventHandler
    public void onCustomEntityDeath(EntityDeathEvent e) {
        if (!e.getEntity().equals(entity)) return;
        if (entityDeathEvent != null) entityDeathEvent.accept(e, this);
    }
}
