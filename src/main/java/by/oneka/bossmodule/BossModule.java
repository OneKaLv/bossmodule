package by.oneka.bossmodule;

import by.oneka.bossmodule.events.onBossDamage;
import by.oneka.bossmodule.events.onBossDeath;
import by.oneka.bossmodule.injector.InjectionModule;
import by.oneka.bossmodule.managers.BossManager;
import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class BossModule extends JavaPlugin {

    BossManager bossesManager;

    @Override
    public void onEnable(){
        bossesManager = new BossManager();
        Injector injector = Guice.createInjector(new InjectionModule(this));
    }

    @Override
    public void onDisable(){
        bossesManager.removeBosses();
    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new onBossDamage(), this);
        Bukkit.getPluginManager().registerEvents(new onBossDeath(), this);
    }

}
