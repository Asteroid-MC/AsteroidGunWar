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

package io.github.asteroidmc.agw.listeners;

import io.github.asteroidmc.agw.AgwPaperPlugin;
import io.github.asteroidmc.agw.object.AgwPlayerDataObject;
import io.github.asteroidmc.agw.object.AgwPlayerObject;
import io.github.asteroidmc.agw.object.AgwPlayerProfileObject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public final class AgwPlayerListener extends PlayerListener {

    @Override
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        UUID id = e.getPlayer().getUniqueId();
        AgwPaperPlugin plugin = AgwPaperPlugin.getInstance();

        AgwPlayerProfileObject profile = new AgwPlayerProfileObject(id);
        AgwPlayerDataObject data = new AgwPlayerDataObject(id);
        AgwPlayerObject obj = new AgwPlayerObject(id, profile, data);
        plugin.addPlayer(id, obj);

        super.onJoin(e);
    }

    @Override
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        UUID id = e.getPlayer().getUniqueId();
        AgwPaperPlugin plugin = AgwPaperPlugin.getInstance();
        plugin.removePlayer(id);

        super.onQuit(e);
    }
}
