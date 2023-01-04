package dev.imgqbbo.straightpunch.commands;

import dev.imgqbbo.straightpunch.StraightPunch;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PunchCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("§7Running §bStraightPunch §7version §b" + StraightPunch.version + " §7by §cImGqbbo");
        return true;
    }
}
