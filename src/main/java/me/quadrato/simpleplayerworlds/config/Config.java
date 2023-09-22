package me.quadrato.simpleplayerworlds.config;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;

public class Config {
    public static File folder, config;
    public static ConfigurationLoader<CommentedConfigurationNode> loader;
    public static CommentedConfigurationNode configNode;

    public static void setup(File myFolder) {
        folder = myFolder;
    }

    public static void load() {
        if (!folder.exists()) {
            folder.mkdir();
        }

        try {
            config = new File(folder, "config.conf");
            loader = HoconConfigurationLoader.builder().setFile(config).build();

            if (!config.exists()) {
                config.createNewFile();
                configNode = loader.load();
                loader.save(configNode);
            }

            configNode = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            loader.save(configNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
