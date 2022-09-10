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

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public final class AgwDataFileManager implements AgwFileManager {

    private final AgwPaperPlugin plugin;
    private final AgwPluginAPI api;
    private final File dataFolder;
    private File langFolder;
    private File playersFolder;

    private final File configFile;

    private FileConfiguration config;

    public AgwDataFileManager(AgwPaperPlugin plugin, AgwPluginAPI api) {
        this.plugin = plugin;
        this.api = api;
        this.dataFolder = plugin.getDataFolder();
        this.langFolder = null;
        this.configFile = new File(dataFolder, "config.yml");
        this.playersFolder = null;
    }

    public void init() {
        try {

            if(!dataFolder.exists()) {
                dataFolder.mkdirs();
            }

            saveDefault(configFile, "config.yml", plugin);
            config = YamlConfiguration.loadConfiguration(configFile);

            langFolder = new File(config.getString("data.langFolder", Utils.path(dataFolder.getPath(), "lang")));
            if(!langFolder.exists()) {
                langFolder.mkdirs();
            }

            playersFolder = new File(config.getString("data.playersFolder", Utils.path(dataFolder.getPath(), "players")));
            if(!playersFolder.exists()) {
                playersFolder.mkdirs();
            }

        } catch(Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public File getDataFolder() {
        return dataFolder;
    }

    @Override
    public File getLangFile() {
        return langFolder;
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }

    @Override
    public File getPlayersFolder() {
        return playersFolder;
    }

    @Override
    public void reloadConfig() {

    }

    @Override
    public void writeDefault(File file, String resource, Plugin plugin) {
        if(!file.exists()) return;
        if(file.isDirectory()) return;
        try (InputStream inputStream = plugin.getResource(resource);
             OutputStream out = Files.newOutputStream(file.toPath())) {
            byte[] buf = new byte['?'];
            int length;
            while ((length = inputStream.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveDefault(File file, String resource, Plugin plugin) {
        if(file.exists()) return;
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeDefault(file, resource, plugin);
    }
}
