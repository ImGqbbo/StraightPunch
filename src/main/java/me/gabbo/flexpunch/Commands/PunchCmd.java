package me.gabbo.flexpunch.Commands;

import me.gabbo.flexpunch.utils.FileUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PunchCmd implements CommandExecutor {

    public static boolean isNumeric(String str) {
        if (str == null) return false;

        try {
            double d = Double.parseDouble(str);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println(FileUtil.ConfigurationMessages.getString("onlyPlayers"));
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("flexpunch.command.punch")) {
            player.sendMessage(FileUtil.ConfigurationMessages.getString("noPermission"));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(FileUtil.ConfigurationMessages.getString("punchCommandUsage"));
            return true;
        }

        switch (args[0]) {
            case "horizontal":
                if (args.length < 2) {
                    player.sendMessage(FileUtil.ConfigurationMessages.getString("defineHorizontalValue"));
                    return true;
                }

                if (!isNumeric(args[1])) {
                    player.sendMessage(FileUtil.ConfigurationMessages.getString("invalidValueUseNumber"));
                    return true;
                }

                FileUtil.ConfigurationFile.set("punch.horizontal", Double.parseDouble(args[1]));
                FileUtil.saveFile(FileUtil.ConfigurationFile, FileUtil.ConfigFile);

                player.sendMessage(FileUtil.ConfigurationMessages.getString("horizontalPunchUpdated"));
                break;
            case "vertical":
                if (args.length < 2) {
                    player.sendMessage(FileUtil.ConfigurationMessages.getString("defineVerticalValue"));
                    return true;
                }

                if (!isNumeric(args[1])) {
                    player.sendMessage(FileUtil.ConfigurationMessages.getString("invalidValueUseNumber"));
                    return true;
                }

                FileUtil.ConfigurationFile.set("punch.vertical", Double.parseDouble(args[1]));
                FileUtil.saveFile(FileUtil.ConfigurationFile, FileUtil.ConfigFile);

                player.sendMessage(FileUtil.ConfigurationMessages.getString("verticalPunchUpdated"));
                break;
            default:
                player.sendMessage(FileUtil.ConfigurationMessages.getString("punchCommandUsage"));
                break;
        }

        return true;
    }
}
