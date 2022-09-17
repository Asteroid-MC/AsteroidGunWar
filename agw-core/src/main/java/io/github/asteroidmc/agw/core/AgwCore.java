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

import io.github.asteroidmc.agw.AgwManager;
import io.github.asteroidmc.agw.AgwPlugin;
import io.github.asteroidmc.agw.data.DataBuilder;
import io.github.asteroidmc.agw.data.DefaultEncoder;
import io.github.asteroidmc.agw.localization.DefaultTexts;
import io.github.asteroidmc.agw.localization.StandardLangs;
import io.github.asteroidmc.agw.localization.UnlocalizedText;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public final class AgwCore {

    private static AgwCore instance;

    private boolean closed;
    private final PluginManager pluginManager;
    private final AgwPlugin plugin;
    private final Logger logger;
    private final AgwCommandCore commandCore;
    private final AgwVersionCore versionCore;

    public AgwCore() {
        instance = this;

        pluginManager = Bukkit.getPluginManager();
        plugin = AgwPlugin.getInstance();
        logger = plugin.getLogger();
        closed = false;

        commandCore = new AgwCommandCore(this);
        versionCore = new AgwVersionCore(this);
    }

    public void init() {
        logger.info("Initializing core...");

        versionCore.init();

        DataBuilder.register(new DataBuilder("def", new DefaultEncoder()));

        for(UnlocalizedText ut : DefaultTexts.getTexts()) {
            AgwPlugin.getInstance().getAgwManager().registerText(ut);
        }

        AgwManager am = AgwPlugin.getInstance().getAgwManager();
        am.registerLang(StandardLangs.EN_US);
        am.registerLang(StandardLangs.JA_JP);
    }

    public void close() {
        closed = true;
    }



    public boolean isClosed() {
        return closed;
    }

    public Logger getLogger() {
        return logger;
    }

    public AgwCommandCore getCommandCore() {
        return commandCore;
    }

    @NotNull
    public static AgwCore getCore() {
        return instance;
    }

}
