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

package io.github.asteroidmc.agw.data;

import io.github.asteroidmc.agw.exceptions.InvalidDataFormatException;

import java.util.ArrayList;
import java.util.List;

public class ReservedBytes {

    public static final List<Byte> bytes = new ArrayList<>();

    public static final byte ESCAPE = d((byte) 0xff);

    public static final byte SEPARATOR_COLUMN = d((byte) 0x1a);
    public static final byte SEPARATOR_ENCODER = d((byte) 0x1b);
    public static final byte SEPARATOR_ARRAY = d((byte) 0x0f);
    public static final byte SR_D = d((byte) 0x20);

    public static final byte BT_B = d((byte) 0x03);
    public static final byte BD_B = d((byte) 0x04);
    public static final byte BT_O = d((byte) 0x30);
    public static final byte BD_O = d((byte) 0x31);

    private static byte d(byte b) {
        bytes.add(b);
        return b;
    }

    public static byte[] escape(byte b) {
        if(bytes.contains(b)) {
            byte[] r = new byte[2];
            r[0] = ESCAPE;
            r[1] = b;
            return r;
        }
        byte[] r = new byte[1];
        r[0] = b;
        return r;
    }

    /**
     * Escapes a byte array.
     *
     * @param b byte array input
     * @return an escaped byte array
     */
    public static byte[] escape(byte[] b) {
        if(b == null || b.length == 0) return new byte[0];
        byte[] r = new byte[b.length];
        int i = 0;
        for(byte a : b) {
            if(i + 5 >= r.length) {
                int l = r.length + 16;
                byte[] n = new byte[l];
                System.arraycopy(r, 0, n, 0, r.length);
                r = n;
            }
            if(bytes.contains(a)) {
                r[i++] = ESCAPE;
                r[i++] = a;
                continue;
            }
            r[i++] = a;
        }
        byte[] n = new byte[i];
        System.arraycopy(r, 0, n, 0, i);
        return n;
    }

    /**
     * Unescapes a byte array.
     *
     * @param b byte array input
     * @return an unescaped byte array
     * @throws InvalidDataFormatException when escape is failed
     */
    public static byte[] unescape(byte[] b) {
        if(b == null || b.length == 0) return new byte[0];
        byte[] r = new byte[b.length];
        int j = 0;
        for(int i = 0; i < b.length; i++) {
            byte a = b[i];
            if(a == ESCAPE) {
                byte nxt = b[i + 1];
                if(bytes.contains(nxt)) {
                    r[j++] = nxt;
                    i++;
                    continue;
                }
                throw new InvalidDataFormatException("Invalid byte array format. [" + i + ": 0x" + Integer.toHexString(a & 0xff) + "]");
            }
            r[j++] = a;
        }
        byte[] n = new byte[j];
        System.arraycopy(r, 0, n, 0, j);
        return n;
    }

}
