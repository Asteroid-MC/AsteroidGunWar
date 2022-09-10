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

package io.github.asteroidmc.agw.object;

import io.github.asteroidmc.agw.localization.AgwLanguage;
import io.github.asteroidmc.agw.localization.StandardLangs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Data
public class AgwPlayerProfileObject implements AgwPlayerProfile {

    private final UUID uuid;
    private String name;
    private Player player;

    @Parameter
    private String nick;
    @Parameter
    private AgwLanguage lang;

    public AgwPlayerProfileObject(UUID uuid) {
        this.uuid = uuid;

        this.name = null;
        Player p = Bukkit.getPlayer(uuid);
        if(p != null) this.name = p.getName();
        this.player = p;

        this.lang = StandardLangs.EN_US;
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public String getNick() {
        return nick;
    }

    @Override
    public AgwLanguage getLang() {
        return lang;
    }

    @Override
    public void refresh() {
        Player p = Bukkit.getPlayer(uuid);
        if(p != null) {
            this.name = p.getName();
            this.player = p;
        }
    }

    @Override
    public void setNick(String nick) {
        this.nick = nick;
    }

    @Override
    public void setLang(AgwLanguage lang) {
        this.lang = lang;
    }
}
