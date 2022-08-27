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

import java.util.ArrayList;
import java.util.List;

public final class DefaultTexts {

    private static final List<UnlocalizedText> texts = new ArrayList<>();

    public static final UnlocalizedText PREFIX = text("prefix");

    public static final UnlocalizedText ERROR_PERMISSION = text("error.permission");
    public static final UnlocalizedText ERROR_UNSUPPORTED_SENDER = text("error.unsupported_sender");

    public static final UnlocalizedText CHAT_PLAYER_SERVER_JOIN = text("chat.player.server_join");
    public static final UnlocalizedText CHAT_PLAYER_SERVER_QUIT = text("chat.player.server_quit");

    public static final UnlocalizedText PLAYER = text("word.player");
    public static final UnlocalizedText ENTITY = text("word.entity");
    public static final UnlocalizedText COMMAND_BLOCK = text("word.command_block");
    public static final UnlocalizedText CONSOLE = text("word.console");
    public static final UnlocalizedText REMOTE_CONSOLE = text("word.remote_console");

    private static UnlocalizedText text(String tag) {
        UnlocalizedText ut = UnlocalizedText.create(tag);
        texts.add(ut);
        return ut;
    }

    public static List<UnlocalizedText> getTexts() {
        return texts;
    }

}
