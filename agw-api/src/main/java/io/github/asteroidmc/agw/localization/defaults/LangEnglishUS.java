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

package io.github.asteroidmc.agw.localization.defaults;

import io.github.asteroidmc.agw.data.StringMap;
import io.github.asteroidmc.agw.localization.AgwLanguage;

import java.io.File;
import java.io.IOException;

public final class LangEnglishUS extends AgwLanguage {

    public LangEnglishUS() {
        super("en_us");
        this.setDisplayName("English (US)");
    }

    @Override
    public void loadLang() {
        super.loadLang();

        File file = langFile();
        if(file.exists()) return;

        StringMap.Builder builder = new StringMap.Builder();

        builder
                .put("prefix", "&9&lA&3&lGW&r &8&l>&r ")
                .put("error.permission", "&cYou have no permission to perform this.")
                .put("error.unsupported_sender", "&cThis can be performed by %supported_senders%.")
                .put("chat.player.server_join", "&a>> &e%player% &7joined the server.")
                .put("chat.player.server_quit", "&c<< &e%player% &7left the server.")
                .put("word.player", "players")
                .put("word.console", "console")
                .put("word.remote_console", "remote console")
                .put("word.command_block", "command blocks")
                .put("word.entity", "entities")
        ;

        this.stringMap = builder.build();

        try {
            this.stringMap.saveJSON(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
