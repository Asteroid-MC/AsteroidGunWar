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

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * A default encoder.<br><br>
 * Supported types:
 * <li>{@code boolean}</li>
 * <li>{@code byte}</li>
 * <li>{@code char}</li>
 * <li>{@code short}</li>
 * <li>{@code int}</li>
 * <li>{@code long}</li>
 * <li>{@code float}</li>
 * <li>{@code double}</li>
 * <li>{@code String}</li>
 * <li>{@code byte[][]}</li>
 */
public class DefaultEncoder implements DataEncoder<Object> {

    public static final byte ARRAY = (byte) 0x01;

    public static final byte TYPE_BOOLEAN = (byte) 0x39;
    public static final byte TYPE_BYTE = (byte) 0x40;
    public static final byte TYPE_CHAR = (byte) 0x41;
    public static final byte TYPE_SHORT = (byte) 0x42;
    public static final byte TYPE_INT = (byte) 0x43;
    public static final byte TYPE_LONG = (byte) 0x44;

    public static final byte TYPE_FLOAT = (byte) 0x50;
    public static final byte TYPE_DOUBLE = (byte) 0x51;

    public static final byte TYPE_STRING = (byte) 0x60;

    @Override
    public byte[] encode(Object original) {
        if(original instanceof Boolean) {
            boolean val = (boolean) original;
            byte[] b = new byte[2];
            b[0] = TYPE_BOOLEAN;
            b[1] = val ? (byte) 0x00 : (byte) 0x01;
            return b;
        }
        if(original instanceof Byte) {
            byte val = (byte) original;
            byte[] ba = ReservedBytes.escape(val);
            byte[] b = new byte[ba.length + 1];
            int i = 0;
            b[i++] = TYPE_BYTE;
            System.arraycopy(ba, 0, b, i, ba.length);
            return b;
        }
        if(original instanceof Character) {
            char val = (char) original;
            byte[] ba = ReservedBytes.escape((byte) val);
            byte[] b = new byte[ba.length + 1];
            int i = 0;
            b[i++] = TYPE_CHAR;
            System.arraycopy(ba, 0, b, i, ba.length);
            return b;
        }
        if(original instanceof Short) {
            short val = (short) original;
            byte[] ba = ReservedBytes.escape(ByteBuffer.allocate(2).putShort(val).array());
            byte[] b = new byte[ba.length + 1];
            int i = 0;
            b[i++] = TYPE_SHORT;
            System.arraycopy(ba, 0, b, i, ba.length);
            return b;
        }
        if(original instanceof Integer) {
            int val = (int) original;
            byte[] ba = ReservedBytes.escape(ByteBuffer.allocate(4).putInt(val).array());
            byte[] b = new byte[ba.length + 1];
            int i = 0;
            b[i++] = TYPE_INT;
            System.arraycopy(ba, 0, b, i, ba.length);
            return b;
        }
        if(original instanceof Long) {
            long val = (long) original;
            byte[] ba = ReservedBytes.escape(ByteBuffer.allocate(8).putLong(val).array());
            byte[] b = new byte[ba.length + 1];
            int i = 0;
            b[i++] = TYPE_LONG;
            System.arraycopy(ba, 0, b, i, ba.length);
            return b;
        }
        if(original instanceof Float) {
            float val = (float) original;
            byte[] ba = ReservedBytes.escape(ByteBuffer.allocate(4).putFloat(val).array());
            byte[] b = new byte[ba.length + 2];
            int i = 1;
            b[i++] = TYPE_FLOAT;
            System.arraycopy(ba, 0, b, i, ba.length);
            return b;
        }
        if(original instanceof Double) {
            double val = (double) original;
            byte[] ba = ReservedBytes.escape(ByteBuffer.allocate(8).putDouble(val).array());
            byte[] b = new byte[ba.length + 1];
            int i = 0;
            b[i++] = TYPE_DOUBLE;
            System.arraycopy(ba, 0, b, i, ba.length);
            return b;
        }
        if(original instanceof String) {
            String val = (String) original;
            byte[] ba = ReservedBytes.escape(val.getBytes(StandardCharsets.UTF_8));
            byte[] b = new byte[ba.length + 1];
            int i = 0;
            b[i++] = TYPE_STRING;
            System.arraycopy(ba, 0, b, i, ba.length);
            return b;
        }
        if(original instanceof byte[][]) {
            byte[][] val = (byte[][]) original;
            int l = 0;
            for(int i = 0; i < val.length; i++) {
                val[i] = ReservedBytes.escape(val[i]);
                l += val[i].length;
            }

            byte[] b = new byte[l + val.length];
            int i = 0;
            b[i++] = ARRAY;
            for(int j = 0; j < val.length; j++) {
                if(j > 0) b[i++] = ReservedBytes.SEPARATOR_ARRAY;
                byte[] v = val[j];
                System.arraycopy(v, 0, b, i, v.length);
                i += v.length;
            }
            return b;
        }
        throw new InvalidDataFormatException("The input data is not supported.");
    }

    @Override
    public Object decode(byte[] b) {
        if(b == null || b.length == 0) return null;
        byte type = b[0];
        if(type == TYPE_BOOLEAN) {
            return b[1] == 0x01;
        }
        byte[] val = new byte[b.length - 1];
        System.arraycopy(b, 1, val, 0, b.length - 1);

        if(type == ARRAY) {
            byte[][] r = new byte[16][];
            byte[] a = new byte[16];
            int j = 0;
            int k = 0;
            for(int i = 0; i < val.length; i++) {
                if(k + 5 >= a.length) {
                    int l = a.length + 16;
                    byte[] n = new byte[l];
                    System.arraycopy(a, 0, n, 0, a.length);
                    a = n;
                }

                byte v = val[i];
                if(v == ReservedBytes.SEPARATOR_ARRAY) {
                    if(i == 0) continue;
                    if(val[i - 1] != ReservedBytes.ESCAPE) {
                        if(j + 5 >= r.length) {
                            int l = r.length + 16;
                            byte[][] n = new byte[l][];
                            System.arraycopy(r, 0, n, 0, r.length);
                            r = n;
                        }

                        byte[] c = new byte[k];
                        System.arraycopy(a, 0, c, 0, k);
                        r[j++] = c;
                        a = new byte[16];
                        k = 0;
                        continue;
                    }
                }

                if (v == ReservedBytes.ESCAPE) {
                    try {
                        if(ReservedBytes.bytes.contains(val[i + 1])) continue;
                    } catch(ArrayIndexOutOfBoundsException e) {
                        throw new InvalidDataFormatException("The input data is not supported");
                    }
                    throw new InvalidDataFormatException("The input data is not supported");
                }

                a[k++] = v;
            }
        }

        val = ReservedBytes.unescape(val);
        if(type == TYPE_BYTE) {
            return val[0];
        }
        if(type == TYPE_CHAR) {
            return (char) val[0];
        }
        if(type == TYPE_STRING) {
            return new String(val, StandardCharsets.UTF_8);
        }

        ByteBuffer bb = ByteBuffer.wrap(val);
        if(type == TYPE_SHORT) {
            return bb.getShort();
        }
        if(type == TYPE_INT) {
            return bb.getInt();
        }
        if(type == TYPE_LONG) {
            return bb.getLong();
        }
        if(type == TYPE_FLOAT) {
            return bb.getFloat();
        }
        if(type == TYPE_DOUBLE) {
            return bb.getDouble();
        }
        throw new InvalidDataFormatException("The input data is not supported.");
    }

    @Override
    public byte[] identifier() {
        return new byte[]{0x00};
    }
}
