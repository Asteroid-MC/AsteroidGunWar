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

import io.github.asteroidmc.agw.localization.AgwLanguage;
import io.github.asteroidmc.agw.xio.StringMap;

import java.io.File;
import java.io.IOException;

public final class LangJapaneseJP extends AgwLanguage {

    public LangJapaneseJP() {
        super("ja_jp");
        this.setDisplayName("日本語");
    }

    @Override
    public void loadLang() {
        super.loadLang();

        File file = langFile();
        if(file.exists()) return;

        StringMap.Builder builder = new StringMap.Builder();

        builder
                .put("prefix", "&9&lA&3&lGW&r &8&l>&r ")
                .put("error.permission", "&c権限がありません。")
                .put("error.unsupported_sender", "&cこの操作は%supported_senders%からのみ実行できます。")
                .put("chat.player.server_join", "&a-> &e%player% &7がサーバーに参加しました。")
                .put("chat.player.server_quit", "&c<- &e%player% &7がサーバーから退出しました。")
                .put("word.player", "プレイヤー")
                .put("word.console", "コンソール")
                .put("word.remote_console", "リモートコンソール")
                .put("word.command_block", "コマンドブロック")
                .put("word.entity", "エンティティ")
        ;

        this.stringMap = builder.build();

        try {
            this.stringMap.saveJSON(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
