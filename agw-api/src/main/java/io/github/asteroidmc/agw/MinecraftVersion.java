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

package io.github.asteroidmc.agw;

public enum MinecraftVersion {

    v1_8(8, "1.8"),
    v1_9(9, "1.9"),
    v1_10(10, "1.10"),
    v1_11(11, "1.11"),
    v1_12(12, "1.12"),
    v1_13(13, "1.13"),
    v1_14(14, "1.14"),
    v1_15(15, "1.15"),
    v1_16(16, "1.16"),
    v1_17(17, "1.17"),
    v1_18(18, "1.18"),
    v1_19(19, "1.19");

    private final int id;
    private final String version;

    MinecraftVersion(int id, String version) {
        this.id = id;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public static MinecraftVersion getVersion(int id) {
        MinecraftVersion version = null;
        switch(id) {
            case 8:
                version = MinecraftVersion.v1_8;
                break;
            case 9:
                version = MinecraftVersion.v1_9;
                break;
            case 10:
                version = MinecraftVersion.v1_10;
                break;
            case 11:
                version = MinecraftVersion.v1_11;
                break;
            case 12:
                version = MinecraftVersion.v1_12;
                break;
            case 13:
                version = MinecraftVersion.v1_13;
                break;
            case 14:
                version = MinecraftVersion.v1_14;
                break;
            case 15:
                version = MinecraftVersion.v1_15;
                break;
            case 16:
                version = MinecraftVersion.v1_16;
                break;
            case 17:
                version = MinecraftVersion.v1_17;
                break;
            case 18:
                version = MinecraftVersion.v1_18;
                break;
            case 19:
                version = MinecraftVersion.v1_19;
        }
        return version;
    }

}
