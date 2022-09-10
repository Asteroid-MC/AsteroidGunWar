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

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Data
public class AgwPlayerObject implements AgwPlayer {

    @Parameter
    private final UUID uuid;
    private String name;
    private Player player;

    @Parameter
    private final AgwPlayerProfileObject profile;
    @Parameter
    private final AgwPlayerDataObject data;

    public AgwPlayerObject(UUID uuid, AgwPlayerProfileObject profile, AgwPlayerDataObject data) {
        this.uuid = uuid;
        this.profile = profile;
        this.data = data;

        this.name = profile.getName();
        Player p = Bukkit.getPlayer(uuid);
        if(p != null) this.name = p.getName();
        this.player = p;
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
    public AgwPlayerProfileObject getProfile() {
        return profile;
    }

    @Override
    public AgwPlayerDataObject getData() {
        return data;
    }

    @Override
    public void refresh() {
        Player p = Bukkit.getPlayer(uuid);
        if(p != null) {
            this.name = p.getName();
            this.player = p;

            this.profile.refresh();
            this.data.refresh();
        }
    }

}
