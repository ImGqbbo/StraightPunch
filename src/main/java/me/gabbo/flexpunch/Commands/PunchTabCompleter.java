package me.gabbo.flexpunch.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PunchTabCompleter implements TabCompleter {
    private static final List<String> subCmds = new ArrayList<String>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        subCmds.clear();

        subCmds.add("horizontal");
        subCmds.add("vertical");

        StringUtil.copyPartialMatches(args[0], subCmds, completions);

        Collections.sort(completions);
        return completions;
    }
}
