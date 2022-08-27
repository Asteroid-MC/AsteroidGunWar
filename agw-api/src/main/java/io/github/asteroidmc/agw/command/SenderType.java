package io.github.asteroidmc.agw.command;

import io.github.asteroidmc.agw.localization.DefaultTexts;
import io.github.asteroidmc.agw.localization.UnlocalizedText;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public enum SenderType {

    CONSOLE(DefaultTexts.CONSOLE, ConsoleCommandSender.class),
    REMOTE(DefaultTexts.REMOTE_CONSOLE, RemoteConsoleCommandSender.class),
    BLOCK(DefaultTexts.COMMAND_BLOCK, BlockCommandSender.class),
    PLAYER(DefaultTexts.PLAYER, Player.class),
    ENTITY(DefaultTexts.ENTITY, Entity.class);

    private final Class<?> clazz;
    private final UnlocalizedText text;

    SenderType(UnlocalizedText text, Class<?> clazz) {
        this.clazz = clazz;
        this.text = text;
    }

    public UnlocalizedText getText() {
        return text;
    }

    public boolean match(CommandSender commandSender) {
        return clazz.isInstance(commandSender);
    }

    public static SenderType getType(CommandSender commandSender) {
        SenderType[] values = SenderType.values();
        for(SenderType type : values) {
            if(type.clazz.isInstance(commandSender)) return type;
        }
        return null;
    }

}
