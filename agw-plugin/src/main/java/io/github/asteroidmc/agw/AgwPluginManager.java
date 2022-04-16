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

package io.github.asteroidmc.agw;

import io.github.asteroidmc.agw.core.AgwCore;
import io.github.asteroidmc.agw.listeners.AgwPlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class AgwPluginManager implements AgwManager {

    private final AgwPaperPlugin plugin;
    private final AgwCore core;
    private final List<Listener> events;
    private final PluginManager pluginManager;

    AgwPluginManager(AgwPaperPlugin plugin) {
        this.plugin = plugin;
        this.core = AgwCore.getCore();
        this.events = new ArrayList<>();
        this.pluginManager = Bukkit.getPluginManager();
    }

    @Override
    public AgwPaperPlugin getPlugin() {
        return plugin;
    }

    void enable() {
        regEv(new AgwPlayerListener());

        core.init();
    }

    public void regEv(Listener listener) {
        events.add(listener);

        pluginManager.registerEvents(listener, plugin);
    }

    void disable() {
        core.close();
    }

    @Override
    public List<Listener> getEvents() {
        return events;
    }
}
