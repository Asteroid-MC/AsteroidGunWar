package io.github.asteroidmc.agw.command.defaults;

import io.github.asteroidmc.agw.command.AgwCommand;
import io.github.asteroidmc.agw.command.CommandCategory;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public final class AgwMainCommand extends AgwCommand {

    public AgwMainCommand() {
        super("asteroidgunwar");
        List<String> aliases = new ArrayList<>();
        aliases.add("agw");
        setAliases(aliases);
        setCategory(CommandCategory.GENERAL);
        setDescription("This is a main command of the plugin.");
    }

    @Override
    public String registeringName() {
        return "asteroidgunwar";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(isExecutable(sender)) {
            sender.sendMessage("");
        }
        return true;
    }
}
