package me.gabbo.flexpunch;

import me.gabbo.flexpunch.Commands.PunchCmd;
import me.gabbo.flexpunch.Commands.PunchTabCompleter;
import me.gabbo.flexpunch.utils.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class FlexPunch extends JavaPlugin {
    public static FlexPunch plugin;

    @Override
    public void onEnable() {
        plugin = this;

        FileUtil.filesCheck();
        FileUtil.configFileLoader();

        getCommand("punch").setExecutor(new PunchCmd());
        getCommand("punch").setTabCompleter(new PunchTabCompleter());

        Bukkit.getServer().getPluginManager().registerEvents(new PunchModule(), this);

        saveDefaultConfig();
    }

    @Override
    public void onDisable()
    {
        plugin = null;
        Bukkit.getPluginManager().disablePlugin(this);
    }
}
