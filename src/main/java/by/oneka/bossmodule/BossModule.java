package by.oneka.bossmodule;

import by.oneka.bossmodule.injector.InjectionModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bukkit.plugin.java.JavaPlugin;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class BossModule extends JavaPlugin {

    @Override
    public void onEnable(){
        Injector injector = Guice.createInjector(new InjectionModule(this));
    }

    @Override
    public void onDisable(){

    }
}
