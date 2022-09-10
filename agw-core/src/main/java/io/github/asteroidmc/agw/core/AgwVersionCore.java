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

import io.github.asteroidmc.agw.MinecraftVersion;
import org.bukkit.Bukkit;

import java.util.logging.Logger;

public class AgwVersionCore {

    private final AgwCore core;
    private final Logger logger;
    private MinecraftVersion version;

    public AgwVersionCore(AgwCore core) {
        this.core = core;
        this.logger = core.getLogger();
        this.version = null;
    }

    void init() {
        try {
            String[] v = Bukkit.getServer().getVersion().split("\\.");
            int id = v.length >= 1 ? Integer.parseInt(v[1].trim()) : -1;
            version = MinecraftVersion.getVersion(id);
        } catch (NumberFormatException ignored) { }
        if(version == null) {
            version = MinecraftVersion.v1_12;
            logger.warning("The version is not supported.");
        }
    }

    public AgwCore getCore() {
        return core;
    }

    public MinecraftVersion getVersion() {
        return version;
    }
}
