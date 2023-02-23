package by.oneka.bossmodule.injector;

import by.oneka.bossmodule.BossModule;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Singleton
public class InjectionModule extends AbstractModule {
    BossModule plugin;

    @Override
    protected void configure(){
        bind(Plugin.class).toInstance(plugin);
    }

}
