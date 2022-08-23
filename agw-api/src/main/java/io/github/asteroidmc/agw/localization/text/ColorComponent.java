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

package io.github.asteroidmc.agw.localization.text;

import org.bukkit.ChatColor;

public final class ColorComponent implements TextComponent<ChatColor> {

    private ChatColor value;

    public ColorComponent(ChatColor value) {
        this.value = value;
    }

    @Override
    public ChatColor getRawComponent() {
        return value;
    }

    @Override
    public void setRawComponent(ChatColor value) {
        this.value = value;
    }

    @Override
    public void applyColor() { }

    @Override
    public String replaceArgument(String arg, String val) {
        return value.toString();
    }

    public String toString() {
        return value.toString();
    }

}