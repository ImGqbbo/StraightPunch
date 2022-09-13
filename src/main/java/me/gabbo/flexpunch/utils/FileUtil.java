package me.gabbo.flexpunch.utils;

import me.gabbo.flexpunch.FlexPunch;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    public static File ConfigFile = new File(FlexPunch.plugin.getDataFolder(), "config.yml");
    public static FileConfiguration ConfigurationFile;
    public static File MessagesFile = new File(FlexPunch.plugin.getDataFolder(), "messages.yml");
    public static FileConfiguration ConfigurationMessages;

    /**
     * Check if a file in the plugin's directory exists
     * @param javaPlugin The plugin
     * @param filePath The file path of the "resources" folder (e.g. config.yml)
     */
    public static boolean fileExists(JavaPlugin javaPlugin, String filePath)
    {
        return new File(javaPlugin.getDataFolder(), filePath).exists();
    }

    public static void saveFile(FileConfiguration fileConfiguration, File file) {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void filesCheck() {
        if(!fileExists(FlexPunch.plugin, "config.yml")) {
            FlexPunch.getPlugin(FlexPunch.class).saveResource("config.yml", false);
        }

        if(!fileExists(FlexPunch.plugin, "messages.yml")) {
            FlexPunch.getPlugin(FlexPunch.class).saveResource("messages.yml", false);
        }
    }

    public static void configFileLoader() {
        ConfigurationFile = YamlConfiguration.loadConfiguration(ConfigFile);
        ConfigurationMessages = YamlConfiguration.loadConfiguration(MessagesFile);
    }
}
