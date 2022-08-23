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

package io.github.asteroidmc.agw.localization;

import io.github.asteroidmc.agw.AgwAPI;
import io.github.asteroidmc.agw.Registerable;

public interface UnlocalizedText extends Registerable {

    /**
     * Creates new unlocalized text for localizing.<br>
     * If you make module or plugin, please add .lang file.
     *
     * @param tag unlocalized text with String
     * @return unlocalized text object
     */
    static UnlocalizedText create(String tag) {
        return AgwAPI.getAPI().createUnlocalizedText(tag);
    }

    String getTag();

    TextLocalizer localizer();

}
