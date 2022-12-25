package dev.imgqbbo.straightpunch;

import dev.imgqbbo.straightpunch.commands.PunchCommand;
import dev.imgqbbo.straightpunch.events.PunchListener;
import dev.imgqbbo.straightpunch.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class StraightPunch extends JavaPlugin {

    @Override
    public void onEnable() {
        instance = this;
        fileManager = new FileManager(instance);
        
        Bukkit.getPluginManager().registerEvents(new PunchListener(), this);
        getCommand("punch").setExecutor(new PunchCommand());
    }

    private static FileManager fileManager;
    public static FileManager getFileManager() {
        return fileManager;
    }

    private static StraightPunch instance;
    public static StraightPunch getInstance() {
        return instance;
    }
}
