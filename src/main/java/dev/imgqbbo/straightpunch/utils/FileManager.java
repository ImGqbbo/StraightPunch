package dev.imgqbbo.straightpunch.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class FileManager {
    public FileManager(Plugin instance) {
        FileManager.instance = instance;
        config = saveConfig("config.yml");
    }

    public void saveConfiguration(FileConfiguration configuration, String name) {
        File file = new File(instance.getDataFolder(), name);
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FileConfiguration saveConfig(String configName) {
        File file = new File(instance.getDataFolder(), configName);

        if (!file.exists()) {
            instance.saveResource(configName, false);
        }

        return loadConfig(configName);
    }

    public static FileConfiguration loadConfig(String configName) {
        YamlConfiguration configuration = new YamlConfiguration();
        File file = new File(instance.getDataFolder(), configName);

        try {
            configuration.load(file);
        } catch (IOException | InvalidConfigurationException ex) {
            ex.printStackTrace();
        }

        return configuration;
    }

    static FileConfiguration config;

    public FileConfiguration getConfig() {
        return config;
    }
    static Plugin instance;
}
