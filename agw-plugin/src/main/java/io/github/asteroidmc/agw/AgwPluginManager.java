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
import io.github.asteroidmc.agw.localization.AgwLg;
import io.github.asteroidmc.agw.localization.UnlocalizedText;
import io.github.asteroidmc.agw.localization.text.AgwComponent;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public final class AgwPluginManager implements AgwManager {

    private final AgwPaperPlugin plugin;
    private final Logger logger;
    AgwCore core;
    private final List<Listener> events;
    private final PluginManager pluginManager;
    private final AgwRegistry<UnlocalizedText> registeredTexts;
    private final AgwRegistry<AgwLg> registeredLangs;

    AgwPluginManager(AgwPaperPlugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.core = AgwCore.getCore();
        this.events = new ArrayList<>();
        this.pluginManager = Bukkit.getPluginManager();
        this.registeredTexts = new AgwRegistry<>();
        this.registeredLangs = new AgwRegistry<>();
    }

    @Override
    public AgwPaperPlugin getPlugin() {
        return plugin;
    }

    void enable() {
        regEv(new AgwPlayerListener());

        AgwPluginAPI.fileManager().init();
        core.init();
    }

    void ready() {
        Collection<AgwLg> langs = getLangs().list();
        for(AgwLg lang : langs) {
            lang.loadLang();
        }
    }

    public void regEv(Listener listener) {
        events.add(listener);

        logger.info("Registered Event Listener: " + listener.getClass().getName());
        pluginManager.registerEvents(listener, plugin);
    }

    void disable() {
        core.close();
    }

    @Override
    public List<Listener> getEvents() {
        return events;
    }

    @Override
    public void registerText(UnlocalizedText text) {
        registeredTexts.register(text);
    }

    @Override
    public void registerLang(AgwLg language) {
        registeredLangs.register(language);
    }

    @Override
    public AgwRegistry<UnlocalizedText> getTexts() {
        return registeredTexts;
    }

    @Override
    public AgwRegistry<AgwLg> getLangs() {
        return registeredLangs;
    }
}
