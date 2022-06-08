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

public final class AgwUnlocalizedText implements UnlocalizedText {

    private String tag;
    private AgwTextLocalizer localizer;

    public AgwUnlocalizedText(String tag) {
        if(tag.matches("[^a-zA-Z0-9_\\-.*]")) throw new IllegalArgumentException("contains invalid characters (valid chars: a-z A-Z 0-9 _ - . *)");
        this.tag = tag;
        this.localizer = new AgwTextLocalizer(this);
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public TextLocalizer localizer() {
        return localizer;
    }

    @Override
    public String registeringName() {
        return tag;
    }
}
