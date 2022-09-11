package me.gabbo.flexpunch.Commands;

import me.gabbo.flexpunch.FlexPunch;
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
            System.out.println("Questo e' un comando eseguibile soltanto dagli utenti!");
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("flexpunch.command.punch")) {
            player.sendMessage("§cNon hai il permesso di eseguire questo comando!");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("§9Flex§fPunch §8» §7By §bImGqbbo\n§cUso del comando: /punch [horizontal/vertical] [valore]");
            return true;
        }

        switch (args[0]) {
            case "horizontal":
                if (args.length < 2) {
                    player.sendMessage("§cDefinisci il punch orizzontale!");
                    return true;
                }

                if (!isNumeric(args[1])) {
                    player.sendMessage("§cValuta orizzontale invalida! Definisci un numero.");
                    return true;
                }

                FlexPunch.config.set("punch.horizontal", Double.parseDouble(args[1]));
                FlexPunch.saveMainConfig();

                player.sendMessage("§aPunch orizzontale aggiornato con successo!");
                break;
            case "vertical":
                if (args.length < 2) {
                    player.sendMessage("§cDefinisci il punch verticale!");
                    return true;
                }

                if (!isNumeric(args[1])) {
                    player.sendMessage("§cValuta verticale invalida! Definisci un numero.");
                    return true;
                }

                FlexPunch.config.set("punch.vertical", Double.parseDouble(args[1]));
                FlexPunch.saveMainConfig();

                player.sendMessage("§aPunch verticale aggiornato con successo!");
                break;
            default:
                player.sendMessage("§9Flex§fPunch §8» §7By §bImGqbbo\n§cUso del comando: /punch [horizontal/vertical] [valore]");
                break;
        }

        return true;
    }
}
