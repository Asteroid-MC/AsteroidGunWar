/*
 * Copyright (c) 2022, ClockClap. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package io.github.asteroidmc.agw.command;

import io.github.asteroidmc.agw.AgwAPI;
import io.github.asteroidmc.agw.AgwPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class AgwCommand extends Command {

    private final AgwPlugin plugin;
    private final AgwAPI api;

    private CommandCategory category = CommandCategory.GENERAL;

    protected AgwCommand(String name) {
        super(name);
        plugin = AgwPlugin.getInstance();
        api = AgwAPI.getAPI();
    }

    protected AgwCommand(String name, CommandCategory category, String description, String usageMessage, List<String> aliases) {
        super(name, description, usageMessage, aliases);
        this.category = category;
        plugin = AgwPlugin.getInstance();
        api = AgwAPI.getAPI();
    }

    public CommandCategory getCategory() {
        return category;
    }

    @Override
    public String getPermission() {
        return category.getPermission() + "." + getName();
    }

    @Override
    public void setPermission(String permission) {

    }

    @Override
    public boolean testPermission(CommandSender target) {
        if (testPermissionSilent(target)) {
            return true;
        }

        String permissionMessage = getPermissionMessage();
        String permission = getPermission();

        if (permissionMessage == null) {
            target.sendMessage(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.");
        } else if (permissionMessage.length() != 0) {
            for (String line : permissionMessage.replace("<permission>", permission).split("\n")) {
                target.sendMessage(line);
            }
        }

        return false;
    }

    @Override
    public boolean testPermissionSilent(CommandSender target) {
        String permission = getPermission();
        return target.hasPermission(permission);
    }

    protected void setCategory(CommandCategory category) {
        this.category = category;
    }

    protected AgwPlugin getPlugin() {
        return plugin;
    }

    protected AgwAPI getAPI() {
        return api;
    }

}
