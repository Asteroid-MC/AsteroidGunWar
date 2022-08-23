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

package io.github.asteroidmc.agw.core.listeners;

import io.github.asteroidmc.agw.AgwPlugin;
import io.github.asteroidmc.agw.localization.DefaultTexts;
import io.github.asteroidmc.agw.localization.StandardLangs;
import io.github.asteroidmc.agw.localization.text.AgwComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        AgwComponent component = new AgwComponent().add(DefaultTexts.CHAT_PLAYER_SERVER_JOIN);
        component.setArgument("player", p.getDisplayName());

        String msg = component.stringify(StandardLangs.JA_JP);
        System.out.println(msg);
        e.setJoinMessage(msg);
    }

    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        AgwComponent component = new AgwComponent().add(DefaultTexts.CHAT_PLAYER_SERVER_QUIT);
        component.setArgument("player", p.getDisplayName());

        e.setQuitMessage(component.stringify(StandardLangs.JA_JP));
    }

}
