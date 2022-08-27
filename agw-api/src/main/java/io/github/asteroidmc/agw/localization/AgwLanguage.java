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
import io.github.asteroidmc.agw.xio.StringMap;

import java.io.File;

public abstract class AgwLanguage implements Registerable {

    private String id;
    private String displayName;
    protected StringMap stringMap;

    protected AgwLanguage(String id) {
        if(id.matches("[^a-zA-Z0-9_]")) throw new IllegalArgumentException("contains invalid characters (valid chars: a-z A-Z _)");
        this.id = this.displayName = id;
        this.stringMap = StringMap.empty();
    }

    public final String getId() {
        return id;
    }

    protected final void setId(String id) {
        this.id = id;
    }

    protected final void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public final String getDisplayName() {
        return displayName;
    }

    public void loadLang() {
        File lf = langFile();
        if(lf.exists()) {
            this.stringMap = StringMap.loadFromJSON(lf);
        }
    }

    public final StringMap translationMap() {
        return stringMap;
    }

    public final File langFile() {
        return new File(AgwAPI.fileManager().getLangFile(), getId() + ".json");
    }

    public Translator translator() {
        AgwLanguage lang = this;
        return new Translator() {
            @Override
            public AgwLanguage getLang() {
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
