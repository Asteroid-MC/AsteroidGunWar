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

import io.github.asteroidmc.agw.localization.AgwLanguage;
import io.github.asteroidmc.agw.localization.UnlocalizedText;
import org.bukkit.event.Listener;

import java.util.List;

public interface AgwManager {

    /**
     * Gets the plugin instance.
     *
     * @return instance
     */
    AgwPlugin getPlugin();

    /**
     * Returns list of events registered in the plugin.<br>
     * Do not add your events to this list.
     *
     * @return list of events
     */
    List<Listener> getEvents();

    /**
     * Registers an unlocalized text for localizing.
     *
     * @param text unlocalized text input
     */
    void registerText(UnlocalizedText text);

    /**
     * Registers an language for localizing any texts
     *
     * @param language language to register
     */
    void registerLang(AgwLanguage language);

    AgwRegistry<AgwLanguage> getLangs();

    AgwRegistry<UnlocalizedText> getTexts();

}
