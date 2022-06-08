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

    public static final UnlocalizedText PREFIX = text("prefix", "&2[AGW]");
    public static final UnlocalizedText ERROR_PERMISSION = text("error.permission", "You have no permission to do this.");

    private static UnlocalizedText text(String tag, String def) {
        UnlocalizedText ut = UnlocalizedText.create(tag);
        texts.add(ut);
        return ut;
    }

    public static List<UnlocalizedText> getTexts() {
        return texts;
    }

}
