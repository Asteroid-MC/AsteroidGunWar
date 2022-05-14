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

package io.github.asteroidmc.agw.command;

public enum CommandCategory {

    GENERAL(0, "agw.command.general", "agw"),
    SYSTEM(10, "agw.command.system", "agw-system"),
    GAME(11, "agw.command.game", "agw-game"),
    CHAT(12, "agw.command.chat", "agw-chat"),
    PLAYER(20, "agw.command.player", "agw-player"),
    PROXY(-1, "agw.command.proxy", "agw-proxy");

    private final int id;
    private final String permission;
    private final String prefix;

    CommandCategory(int id, String permission, String prefix) {
        this.id = id;
        this.permission = permission;
        this.prefix = prefix;
    }

    public int getId() {
        return id;
    }

    public String getPermission() {
        return permission;
    }

    public String getPrefix() {
        return prefix;
    }

}
