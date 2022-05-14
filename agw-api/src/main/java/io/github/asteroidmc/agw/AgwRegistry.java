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

import java.util.HashMap;
import java.util.Map;

public class AgwRegistry<T extends Registerable> {

    private final Map<String, T> map;

    public AgwRegistry() {
        this.map = new HashMap<>();
    }

    public void register(T t) {
        String rn = t.registeringName().toLowerCase();
        if(map.containsKey(rn)) throw new IllegalArgumentException("already registered: " + t.registeringName());

        map.put(rn, t);
    }

    public void unregister(String id) {
        String rn = id.toLowerCase();
        if(!map.containsKey(rn)) throw new IllegalArgumentException("not registered yet: " + id);

        map.remove(rn);
    }

    public boolean isRegistered(String id) {
        String rn = id.toLowerCase();
        return map.containsKey(rn);
    }

    public T get(String id) {
        String rn = id.toLowerCase();
        if(!map.containsKey(rn)) throw new IllegalArgumentException("not registered yet: " + id);

        return map.get(rn);
    }

}
