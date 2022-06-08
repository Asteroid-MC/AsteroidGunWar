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

import io.github.asteroidmc.agw.Registerable;
import io.github.asteroidmc.agw.xio.StringMap;

public abstract class AgwLg implements Registerable {

    private String id;
    private StringMap stringMap;

    protected AgwLg(String id) {
        if(id.matches("[^a-zA-Z0-9_]")) throw new IllegalArgumentException("contains invalid characters (valid chars: a-z A-Z _)");
        this.id = id;
        this.stringMap = StringMap.empty();
    }

    public final String getId() {
        return id;
    }

    protected final void setId(String id) {
        this.id = id;
    }

    public abstract void loadLang();

    public final StringMap translationMap() {
        return stringMap;
    }

    public Translator translator() {
        AgwLg lang = this;
        return new Translator() {
            @Override
            public AgwLg getLang() {
                return lang;
            }

            @Override
            public String translate(TextLocalizer localizer) {
                UnlocalizedText ut = localizer.getText();
                String key = ut.getTag();
                return translationMap().get(key, key);
            }
        };
    }

    @Override
    public final String registeringName() {
        return id;
    }
}
