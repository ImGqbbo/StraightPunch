package me.gabbo.flexpunch;

import me.gabbo.flexpunch.Commands.PunchCmd;
import me.gabbo.flexpunch.Commands.PunchTabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class FlexPunch extends JavaPlugin {
    public static Plugin plugin;
    public static File configFile;
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        plugin = this;

        getCommand("punch").setExecutor(new PunchCmd());
        getCommand("punch").setTabCompleter(new PunchTabCompleter());

        Bukkit.getServer().getPluginManager().registerEvents(new PunchModule(), this);

        saveDefaultConfig();
        createFileConfig();
    }

    public static void createFileConfig() {
        configFile = new File(plugin.getDataFolder(), "config.yml");

        if(!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("config.yml", false);
        }

        config = new YamlConfiguration();

        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void saveMainConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
