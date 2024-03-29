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

import io.github.asteroidmc.agw.xio.XRL;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public interface AgwFileManager {

    default File newFile(String... paths) {
        String path = XRL.mergePaths(paths);
        return new File(path);
    }

    File getDataFolder();

    XRL fileToXRL(File file);

    File XRLToFile(XRL xrl);

    File getLangFile();

    FileConfiguration getConfig();

    void reloadConfig();

    void writeDefault(File file, String resource, Plugin plugin);

    void saveDefault(File file, String resource, Plugin plugin);

    void writeDefault(XRL xrl, String resource, Plugin plugin);

    void saveDefault(XRL xrl, String resource, Plugin plugin);

}
