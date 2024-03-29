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

import io.github.asteroidmc.agw.localization.UnlocalizedText;
import org.jetbrains.annotations.Nullable;

public interface AgwAPI {

    /**
     * Returns the API is closed.<br>
     * If the API is closed, you cannot use all of API features.
     *
     * @return true if the API is closed,<br>
     * false if the API is not closed
     */
    boolean isClosed();

    /**
     * Creates new unlocalized text for localizing.<br>
     * If you make module or plugin, please add .lang file.
     *
     * @param tag unlocalized text with String
     * @return unlocalized text object
     */
    UnlocalizedText createUnlocalizedText(String tag);

    /**
     * Gets the instance of API.
     *
     * @return instance
     */
    static AgwAPI getAPI() {
        return AgwPlugin.api;
    }

    static AgwFileManager fileManager() {
        return AgwPlugin.instance.getFileManager();
    }

}
