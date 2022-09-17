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

import io.github.asteroidmc.agw.AgwRegistry;
import io.github.asteroidmc.agw.Registerable;
import io.github.asteroidmc.agw.exceptions.InvalidDataFormatException;

import java.util.HashMap;
import java.util.Map;

public class DataBuilder implements Registerable {

    private static final AgwRegistry<DataBuilder> builders = new AgwRegistry<>();
    private static final Map<byte[], DataBuilder> buildersMap = new HashMap<>();

    private final String name;
    private final DataEncoder<?> encoder;
    private final byte[] identifier;

    public DataBuilder(String name, DataEncoder<?> encoder) {
        this.name = name;
        this.encoder = encoder;
        this.identifier = ReservedBytes.escape(encoder.identifier());
    }

    public DataEncoder<?> encoder() {
        return encoder;
    }

    public byte[] build(Object o) {
        byte[] e = encoder.encode(o);
        byte[] id = identifier();
        byte[] r = new byte[e.length + id.length + 1];
        System.arraycopy(id, 0, r, 0, id.length);
        r[id.length] = ReservedBytes.SEPARATOR_ENCODER;
        System.arraycopy(e, 0, r, id.length + 1, e.length);
        return r;
    }

    public byte[] buildArray(Object[] array) {
        return encoder.encode(buildEach(array));
    }

    public byte[][] buildEach(Object[] o) {
        byte[][] r = new byte[o.length][];
        int i = 0;
        for(Object c : o) {
            r[i++] = build(c);
        }
        return r;
    }

    private Object restore0(byte[] b) {
        byte[] id = identifier();
        byte[] n = new byte[b.length - id.length - 1];
        System.arraycopy(b, id.length + 1, n, 0, n.length);
        return n;
    }

    private Object[] restoreArray0(byte[] b) {
        return restoreEach0((byte[][]) restore0(b));
    }

    private Object[] restoreEach0(byte[][] b) {
        Object[] r = new Object[b.length];
        int i = 0;
        for(byte[] a : b) {
            r[i++] = restore0(a);
        }
        return r;
    }

    public static Object restore(byte[] b) {
        DataBuilder builder = getEncoder(b);
        if(builder == null) return null;
        return builder.restore0(b);
    }

    public static Object[] restoreEach(byte[][] b) {
        Object[] r = new Object[b.length];
        int i = 0;
        for(byte[] a : b) {
            r[i++] = restore(a);
        }
        return r;
    }

    public static Object[] restoreArray(byte[] b) {
        DataBuilder builder = getEncoder(b);
        if(builder == null) return new Object[0];
        return builder.restoreEach0((byte[][]) builder.restore0(b));
    }

    public static DataBuilder getEncoder(byte[] data) {
        byte[] s = new byte[16];
        int j = 0;
        for(int i = 0; i < data.length; i++) {
            if(i + 5 >= s.length) {
                int l = s.length + 16;
                byte[] n = new byte[l];
                System.arraycopy(s, 0, n, 0, s.length);
                s = n;
            }
            byte val = data[i];
            if(val == ReservedBytes.ESCAPE) {
                try {
                    if (ReservedBytes.bytes.contains(data[i + 1])) {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new InvalidDataFormatException("The input data is not supported.");
                }
                throw new InvalidDataFormatException("The input data is not supported.");
            }
            if(val == ReservedBytes.SEPARATOR_ENCODER) {
                if(i == 0) return null;
                if(data[i - 1] != ReservedBytes.ESCAPE) {
                    break;
                }
            }
            s[j++] = val;
        }
        byte[] r = new byte[j];
        System.arraycopy(s, 0, r, 0, j);
        return get(r);
    }

    @Override
    public String registeringName() {
        return name;
    }

    public byte[] identifier() {
        return identifier;
    }

    public static AgwRegistry<DataBuilder> register(DataBuilder item) {
        builders.register(item);
        buildersMap.put(item.identifier, item);
        return builders;
    }

    public static AgwRegistry<DataBuilder> unregister(String name) {
        DataBuilder builder = builders.get(name);
        builders.unregister(name);
        buildersMap.remove(builder.identifier);
        return builders;
    }

    public static DataBuilder get(String name) {
        return builders.get(name);
    }

    public static DataBuilder get(byte[] id) {
        return buildersMap.get(id);
    }

    public static boolean isRegistered(String name) {
        return builders.isRegistered(name);
    }

    public static boolean isRegistered(byte[] id) {
        return buildersMap.containsKey(id);
    }
}
