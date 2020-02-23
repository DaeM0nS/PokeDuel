package com.lypaka.pokeduel.Config;

import com.lypaka.pokeduel.PokeDuel;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.scheduler.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Loads and stores all the configuration settings.
 * It loads from file on server start up. or when a player reloads the plugin.
 *
 * @uathor landonjw
 * @since 9/25/2019 - Version 1.0.0
 */
public class ConfigManager {

    /** Name of the file to grab configuration settings from. */
    private static final String[] FILE_NAMES = {"pokeduel.conf"};

    /** Paths needed to locate the configuration file. */
    private static Path dir, config;
    /** Loader for the configuration file. */
    private static ConfigurationLoader<CommentedConfigurationNode> configLoad;
    /** Storage for all the configuration settings. */
    private static CommentedConfigurationNode configNode;

    /**
     * Locates the configuration file and loads it.
     * @param folder Folder where the configuration file is located.
     */
    public static void setup(Path folder){
        dir = folder;
        for (int i = 0; i <= FILE_NAMES.length - 1; i++) {
            config = dir.resolve(FILE_NAMES[i]);
        }
        load();
    }

    /**
     * Loads the configuration settings into storage.
     */
    public static void load(){
        //Create directory if it doesn't exist.
        try{
            if(!Files.exists(dir)){
                Files.createDirectory(dir);
            }

            //Create or locate file and load configuration file into storage.
            for (int i = 0; i <= FILE_NAMES.length - 1; i++) {
               PokeDuel.getContainer().getAsset(FILE_NAMES[i]).get().copyToFile(config, false, true);
            }


            configLoad = HoconConfigurationLoader.builder().setPath(config).build();

            configNode = configLoad.load();
        }
        catch (IOException e){
            PokeDuel.getLogger().error("PokeDuel configuration could not load.");
            e.printStackTrace();
        }
    }

    /**
     * Saves the configuration settings to configuration file.
     */
    public static void save(){
        Task save = Task.builder().execute(() -> {

            try{
                configLoad.save(configNode);
            }
            catch(IOException e){
                PokeDuel.getLogger().error("PokeDuel could not save configuration.");
                e.printStackTrace();
            }

        }).async().submit(PokeDuel.instance);
    }

    /**
     * Gets the configuration loader.
     * @return The configuration loader.
     */
    public static ConfigurationLoader<CommentedConfigurationNode> getConfigLoad(){
        return configLoad;
    }

    /**
     * Gets a node from the configuration node, where all configuration settings are stored.
     * @param node A node within the configuration node.
     * @return A node within the configuration node.
     */
    public static CommentedConfigurationNode getConfigNode(Object... node){
        return configNode.getNode(node);
    }
}