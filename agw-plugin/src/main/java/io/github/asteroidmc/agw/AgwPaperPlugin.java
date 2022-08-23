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

public final class AgwPaperPlugin extends AgwPlugin {

    private static AgwPaperPlugin instance;
    private static AgwCore core;

    private static AgwPluginAPI api;

    private AgwPluginManager agwManager;
    private AgwDataFileManager fileManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        AgwPlugin.instance = this;
        instance = this;
        agwManager = new AgwPluginManager(this);
        core = new AgwCore();
        api = new AgwPluginAPI();
        AgwPlugin.api = new AgwPluginAPI();
        fileManager = new AgwDataFileManager(this, api);

        agwManager.core = core;

        agwManager.enable();

        getServer().getScheduler().scheduleSyncDelayedTask(this, () -> agwManager.ready());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        agwManager.disable();
        AgwPluginAPI.getInstance().closeAPI();
    }

    public static AgwPaperPlugin getInstance() {
        return instance;
    }

    @Override
    public AgwDataFileManager getFileManager() {
        return fileManager;
    }

    public AgwCore getCore() {
        return core;
    }

    @Override
    public AgwPluginManager getAgwManager() {
        return agwManager;
    }
}
