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

package io.github.asteroidmc.agw.core;

import io.github.asteroidmc.agw.AgwAPI;
import io.github.asteroidmc.agw.AgwManager;
import io.github.asteroidmc.agw.AgwPlugin;
import io.github.asteroidmc.agw.core.listeners.PlayerListener;
import io.github.asteroidmc.agw.localization.DefaultTexts;
import io.github.asteroidmc.agw.localization.StandardLangs;
import io.github.asteroidmc.agw.localization.UnlocalizedText;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public final class AgwCore {

    private static AgwCore instance;

    private boolean closed;
    private PluginManager pluginManager;
    private AgwPlugin plugin;
    private Logger logger;

    public AgwCore() {
        instance = this;

        pluginManager = Bukkit.getPluginManager();
        plugin = AgwPlugin.getInstance();
        logger = plugin.getLogger();
        closed = false;
    }

    public void init() {
        logger.info("Initializing core...");

        for(UnlocalizedText ut : DefaultTexts.getTexts()) {
            AgwPlugin.getInstance().getAgwManager().registerText(ut);
        }

        AgwManager am = AgwPlugin.getInstance().getAgwManager();
        am.registerLang(StandardLangs.EN_US);
    }

    public void close() {
        closed = true;
    }

    public boolean isClosed() {
        return closed;
    }

    @NotNull
    public static AgwCore getCore() {
        return instance;
    }

}
