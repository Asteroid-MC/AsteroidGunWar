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

import io.github.asteroidmc.agw.localization.AgwLanguage;
import io.github.asteroidmc.agw.localization.UnlocalizedText;
import org.bukkit.ChatColor;

public class InternationalComponent implements TextComponent<UnlocalizedText> {

    private UnlocalizedText value;
    private String str;

    public InternationalComponent(UnlocalizedText value) {
        this.value = value;
        this.str = value.getTag();
    }

    @Override
    public UnlocalizedText getRawComponent() {
        return value;
    }

    @Override
    public void setRawComponent(UnlocalizedText value) {
        this.value = value;
        this.str = value.getTag();
    }

    @Override
    public void applyColor() {
        this.str = ChatColor.translateAlternateColorCodes('&', this.str);
    }

    public String getString() {
        return this.str;
    }

    public void format(AgwLanguage lang) {
        this.str = value.localizer().format(lang);
    }

    public void format(String lang) {
        this.str = value.localizer().format(lang);
    }

    @Override
    public String replaceArgument(String arg, String val) {
        this.str = this.str.replaceAll("\\Q%" + arg + "%\\E", val);
        return this.str;
    }

    public String toString() {
        return this.str;
    }

}
