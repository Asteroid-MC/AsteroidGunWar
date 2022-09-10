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

import com.google.gson.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class StringMap extends HashMap<String, String> {

    private static final long serialVersionUID = 2780338389799783397L;

    public static class Builder {

        private final int initialCapacity;
        private final float loadFactor;
        private final Map<String, String> map;

        public Builder() {
            this(16, 0.75f);
        }

        public Builder(int initialCapacity, float loadFactor) {
            map = new HashMap<>();

            this.initialCapacity = initialCapacity;
            this.loadFactor = loadFactor;
        }

        public Builder put(String key, String value) {
            map.put(key, value);

            return this;
        }

        public Builder remove(String key) {
            map.remove(key);

            return this;
        }

        public String get(String key) {
            return map.get(key);
        }

        public String getAndPut(String key, String value) {
            String v = map.get(key);
            map.put(key, value);
            return v;
        }

        public Set<Entry<String, String>> entrySet() {
            return map.entrySet();
        }

        public Collection<String> values() {
            return map.values();
        }

        public Set<String> keySet() {
            return map.keySet();
        }

        public StringMap build() {
            StringMap result = new StringMap(initialCapacity, loadFactor);

            for(Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                result.add(key, value);
            }

            return result;
        }

    }

    StringMap() {
        super();
    }

    StringMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    @Deprecated
    @Override
    public String put(String key, String value) {
        return null;
    }

    @Deprecated
    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
    }

    @Deprecated
    @Override
    public boolean remove(Object key, Object value) {
        return false;
    }

    @Deprecated
    @Override
    public String remove(Object key) {
        return null;
    }

    @Deprecated
    @Override
    public String putIfAbsent(String key, String value) {
        return null;
    }

    /**
     * Updates a value that the input key has. This method doesn't add a new key.
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        validateKey(key);
        super.put(key, value);
    }

    /**
     * Returns a value of the input key. This method returns null if the key has no value.
     *
     * @param key key input
     * @return value that the key has
     */
    @Deprecated
    @Override
    public String get(Object key) {
        if (key instanceof String) {
            return get((String) key);
        }
        return null;
    }

    /**
     * Returns a value of the input key. This method returns an empty {@code String} if the key has no value.
     *
     * @param key key input
     * @return value that the key has
     */
    @NotNull
    public String get(String key) {
        validateKey(key);
        return get(key, "");
    }

    /**
     * Validates object includes the key. Throws {@code IllegalArgumentException} if the key doesn't exist.
     *
     * @param key the key input
     */
    public void validateKey(String key) {
        if(!containsKey(key)) throw new IllegalArgumentException("unknown key");
    }

    /**
     * Returns a value of the input key as {@code boolean}. This method returns false if the key has no value.
     *
     * @param key key input
     * @return true if the {@code String} value equals "true"; false if the {@code String} value equals "false" or not boolean
     */
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * Returns a value of the input key. This method returns a value of {@code def} if the key has no value.
     *
     * @param key key input
     * @param def default value
     * @return value that the key has
     */
    @NotNull
    public String get(String key, String def) {
        if (containsKey(key)) {
            return super.get(key);
        }
        return def;
    }

    /**
     * Returns a value of the input key as {@code boolean}. This method returns a value of {@code def} if the key has no value.
     *
     * @param key key input
     * @param def default value
     * @return true if the {@code String} value equals "true"; false if the {@code String} value equals "false";
     * default value if the {@code String} value is not boolean
     */
    public boolean getBoolean(String key, boolean def) {
        String v = get(key);
        if (v.equalsIgnoreCase("true")) return true;
        if (v.equalsIgnoreCase("false")) return false;
        return def;
    }

    void add(String key, String value) {
        if (!containsKey(key)) {
            super.put(key.trim(), value.trim());
        }
    }

    @NotNull
    public String asString() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Entry<String, String> entry : entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (i == -1) sb.append(System.lineSeparator());
            sb.append(key);
            sb.append("=");
            sb.append(value);
            if (i == 0) i = -1;
        }
        return sb.toString();
    }

    @NotNull
    public String asString(Charset charset) {
        return new String(asString().getBytes(), charset);
    }

    @NotNull
    public String asString(String charset) throws UnsupportedEncodingException {
        return new String(asString().getBytes(), charset);
    }

    /**
     * Returns the object as {@code HashMap}.
     *
     * @return {@code HashMap} object
     */
    public HashMap<String, String> hashMap() {
        HashMap<String, String> hashMap = new HashMap<>(size(), 1.0F);
        for (Entry<String, String> entry : entrySet())
            hashMap.put(entry.getKey(), entry.getValue());
        return hashMap;
    }

    /**
     * Returns a new {@code StringMap} object from another object.
     * This method reads all fields of an input object and outputs them as {@code StringMap} object.
     * Exceptions will be all caught. If the execution failed, returns an empty {@code StringMap} data.
     *
     * @param obj an object input
     * @param type type of the object
     * @return generated {@code StringMap} object
     */
    public static StringMap fromObject(Object obj, Class<?> type) {
        Field[] fields = type.getDeclaredFields();
        StringMap map = new StringMap(fields.length, 1.0F);
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (!Modifier.isTransient(field.getModifiers())) {
                    Object val = field.get(obj);
                    if (val instanceof String) {
                        map.add(field.getName(), (String) val);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * Returns {@code StringMap} data loaded from a file includes JSON data.
     * This method creates a new instance of {@code StringMap} from the JSON data.
     * Exceptions will be all caught.
     *
     * @param file
     * @return
     */
    @NotNull
    public static StringMap loadFromJSON(File file) {
        StringMap result = null;
        try (StringMapReader reader = new StringMapReader(file, StandardCharsets.UTF_8)) {
            result = reader.readJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result == null) return new StringMap();
        return result;
    }

    /**
     * Returns {@code StringMap} data loaded from a file includes JSON data. This method appends them to the original object. Exceptions will be all caught.
     *
     * @param file a file input includes JSON data
     * @return loaded data of StringMap (not including the original object)
     */
    @NotNull
    public StringMap loadAndAppend(File file) {
        StringMap result = null;

        try (StringMapReader reader = new StringMapReader(file, StandardCharsets.UTF_8)) {
            result = reader.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (result == null) return new StringMap();
        super.putAll(result);

        return result;
    }

    /**
     * Converts {@code StringMap} object to JSON data.
     *
     * @return generated JSON data
     */
    public String toJSON() {
        Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create();
        JsonObject jsonObject = new JsonObject();

        for(Entry<String, String> entry : entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            jsonObject.addProperty(key, value);
        }

        return gson.toJson(jsonObject);
    }

    /**
     * Converts {@code StringMap} object to JSON data and save them to the file.
     *
     * @param file the file
     * @throws IOException If an I/O error occurred
     */
    public void saveJSON(File file) throws IOException {
        String json = toJSON();

        if(!file.exists()) file.createNewFile();
        try(FileOutputStream out = new FileOutputStream(file)) {
            byte[] buf = json.getBytes(StandardCharsets.UTF_8);

            out.write(buf, 0, buf.length);
            out.flush();
        }
    }

    /**
     * Returns an empty {@code StringMap} data.
     *
     * @return an empty data
     */
    @NotNull
    public static StringMap empty() {
        return new StringMap();
    }

    @Deprecated
    @Override
    public void clear() {
    }
}
