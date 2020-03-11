package com.lypaka.pokeduel;

import com.google.inject.Inject;
import com.lypaka.pokeduel.Commands.PokeDuelCommand;
import com.lypaka.pokeduel.Config.ConfigManager;
import com.lypaka.pokeduel.Listeners.BattleEndListener;
import com.pixelmonmod.pixelmon.Pixelmon;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.nio.file.Path;

@Plugin(
        id = "pokeduel",
        name = "PokeDuel",
        version = "2019.2-1.3.2-DaeM0nS-Reforged",
        authors = {
        		"Lypaka",
        		"DaeM0nS"
        }
)
public class PokeDuel {

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path dir;

    @Inject
    public Logger logger;

    @Inject
    private PluginContainer container;

    public static PokeDuel instance;



    @Listener
    public void onPreInit (GamePreInitializationEvent event) {
        logger.info("PokeDuel is starting!");
        instance = this;
        container = Sponge.getPluginManager().getPlugin("pokeduel").get();
        ConfigManager.setup(dir);
        PokeDuelCommand.registerDuelCommand();
        Pixelmon.EVENT_BUS.register(new BattleEndListener());
    }


    public static PluginContainer getContainer() {
        return instance.container;
    }

    public static Logger getLogger() {
        return instance.logger;
    }

}
