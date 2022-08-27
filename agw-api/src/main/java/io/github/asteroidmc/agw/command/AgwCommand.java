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
import io.github.asteroidmc.agw.Registerable;
import io.github.asteroidmc.agw.localization.AgwLanguage;
import io.github.asteroidmc.agw.localization.DefaultTexts;
import io.github.asteroidmc.agw.localization.StandardLangs;
import io.github.asteroidmc.agw.localization.text.AgwComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AgwCommand extends Command implements Registerable {

    private final AgwPlugin plugin;
    private final AgwAPI api;

    private CommandCategory category = CommandCategory.GENERAL;
    private AgwComponent permissionMessageComponent = null;
    private AgwComponent senderMessage = null;
    private List<SenderType> allowedSenders;

    protected AgwCommand(String name) {
        super(name);
        plugin = AgwPlugin.getInstance();
        api = AgwAPI.getAPI();
        allowedSenders = Arrays.asList(SenderType.values());
    }

    protected AgwCommand(String name, CommandCategory category, String description, String usageMessage, List<String> aliases) {
        super(name, description, usageMessage, aliases);
        this.category = category;
        plugin = AgwPlugin.getInstance();
        api = AgwAPI.getAPI();
        allowedSenders = new ArrayList<>();
    }

    public CommandCategory getCategory() {
        return category;
    }

    @Override
    public String getPermission() {
        return category.getPermission() + "." + getName();
    }

    @Override
    public void setPermission(String permission) { }

    public AgwComponent getPermissionMessageComponent() {
        return permissionMessageComponent;
    }

    public AgwComponent getSenderMessage() {
        return senderMessage;
    }

    public void setSenderMessage(AgwComponent component) {
        this.senderMessage = component;
    }

    public AgwCommand setAllowedSenders(List<SenderType> senders) {
        this.allowedSenders = senders;
        return this;
    }

    public AgwCommand allowSender(SenderType sender) {
        allowedSenders.add(sender);
        return this;
    }

    public AgwCommand disallowSender(SenderType sender) {
        allowedSenders.remove(sender);
        return this;
    }

    public boolean testSender(CommandSender target, List<SenderType> allowedSenders) {
        if(testSenderSilent(target)) {
            return true;
        }
        AgwLanguage lang = StandardLangs.JA_JP;

        AgwComponent senderMsg = getSenderMessage();

        List<String> l = new ArrayList<>();
        for(SenderType sender : allowedSenders)
            l.add(sender.getText().localizer().format(lang));
        String senders = String.join(", ", l);

        if(senderMsg == null) {
            senderMsg = new AgwComponent();
            senderMsg.append(DefaultTexts.PREFIX, " ", DefaultTexts.ERROR_UNSUPPORTED_SENDER);
        }
        senderMsg.setArgument("allowed_senders", senders);
        senderMsg.setArgument("sender", SenderType.getType(target));

        if (senderMsg.size() != 0) {
            String msg = senderMsg.stringify(lang);
            for (String line : msg.split("\n")) {
                target.sendMessage(line);
            }
        }

        return false;
    }

    public boolean testSender(CommandSender target) {
        return testSender(target, allowedSenders);
    }

    public boolean testSenderSilent(CommandSender target, List<SenderType> allowedSenders) {
        for(SenderType sender : allowedSenders)
            if(!sender.match(target)) return false;
        return true;
    }

    public boolean testSenderSilent(CommandSender target) {
        return testSenderSilent(target, allowedSenders);
    }

    public boolean isExecutableSilent(CommandSender target, List<SenderType> allowedSenders) {
        return testSenderSilent(target, allowedSenders) && testPermissionSilent(target);
    }

    public boolean isExecutableSilent(CommandSender target) {
        return isExecutableSilent(target, allowedSenders);
    }

    public boolean isExecutable(CommandSender target, List<SenderType> allowedSenders) {
        return testSender(target, allowedSenders) && testPermission(target);
    }

    public boolean isExecutable(CommandSender target) {
        return isExecutable(target, allowedSenders);
    }

    @Override
    public boolean testPermission(CommandSender target) {
        if (testPermissionSilent(target)) {
            return true;
        }
        AgwLanguage lang = StandardLangs.JA_JP;

        AgwComponent permissionMessage = getPermissionMessageComponent();
        String permission = getPermission();
        if(permissionMessage == null){
            if(getPermissionMessage() == null) {
                permissionMessage = new AgwComponent();
                permissionMessage.append(DefaultTexts.PREFIX, " ", DefaultTexts.ERROR_PERMISSION);
            } else permissionMessage = new AgwComponent().append(getPermissionMessage());
        }
        permissionMessage.setArgument("permission", permission);

        if (permissionMessage.size() != 0) {
            String msg = permissionMessage.stringify(lang);
            for (String line : msg.split("\n")) {
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

    protected void setPermissionMessageComponent(AgwComponent component) {
        this.permissionMessageComponent = component;
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
