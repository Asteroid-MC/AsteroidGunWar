package io.github.asteroidmc.agw.core;

import io.github.asteroidmc.agw.AgwRegisteringQueue;
import io.github.asteroidmc.agw.AgwRegistry;
import io.github.asteroidmc.agw.command.AgwCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.util.Collection;

public final class AgwCommandCore {

    private final AgwCore core;
    private final AgwRegistry<AgwCommand> commandRegistry;
    private final AgwRegisteringQueue<AgwCommand> registeringQueue;

    AgwCommandCore(AgwCore core) {
        this.core = core;
        this.commandRegistry = new AgwRegistry<>();
        this.registeringQueue = new AgwRegisteringQueue<>(this.commandRegistry);

        core.getLogger().info("Command Core is ready.");
    }

    public AgwCommandCore queue(AgwCommand... commands) {
        if(commands.length > 0) {
            for(AgwCommand command : commands) registeringQueue.queue(command);
        }
        return this;
    }

    public void rejectQueue() {
        registeringQueue.reject();
    }

    public void cancel(String name) {
        registeringQueue.cancel(name);
    }

    public void register() {
        registeringQueue.registerAll((command) -> {
            String fallbackPrefix = command.getCategory().getPrefix();

            CommandMap map = Bukkit.getServer().getCommandMap();
            map.getKnownCommands().put(command.getName(), command);
            for (String alias : command.getAliases()) {
                map.getKnownCommands().put(alias, command);
            }
            Bukkit.getServer().getCommandMap().register(fallbackPrefix, command);
        });
    }

    public AgwCore getCore() {
        return core;
    }

    public Collection<AgwCommand> list() {
        return commandRegistry.list();
    }

}
