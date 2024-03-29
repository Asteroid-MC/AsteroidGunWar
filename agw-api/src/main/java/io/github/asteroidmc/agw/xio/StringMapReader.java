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

package io.github.asteroidmc.agw.xio;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;

public final class StringMapReader implements Closeable {

    private final String charset;
    private final File file;
    private final FileInputStream inputStream;
    private final InputStreamReader streamReader;
    private final BufferedReader bufferedReader;

    public StringMapReader(File file, Charset charset) throws FileNotFoundException {
        this.file = file;
        this.charset = charset.name();
        this.inputStream = new FileInputStream(file);
        this.streamReader = new InputStreamReader(inputStream, charset);
        this.bufferedReader = new BufferedReader(streamReader);
    }

    public StringMapReader(File file, String charset) throws FileNotFoundException, UnsupportedEncodingException {
        this.file = file;
        this.charset = charset;
        this.inputStream = new FileInputStream(file);
        this.streamReader = new InputStreamReader(inputStream, charset);
        this.bufferedReader = new BufferedReader(streamReader);
    }

    public File getFile() {
        return file;
    }

    public String getEncoding() {
        return charset;
    }

    public StringMap readFile() throws IOException {
        String line;
        StringMap map = new StringMap();
        while((line = bufferedReader.readLine()) != null) {
            if(line.startsWith("#")) continue;
            String[] strs = line.split("=");
            if(strs.length >= 2) {
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i < strs.length; i++) {
                    if(i > 1) sb.append("=");
                    sb.append(strs[i]);
                }
                if (strs.length == 2) map.add(strs[0], strs[1]);
            }
        }
        return map;
    }

    public StringMap readJSON() throws IOException {
        StringMap map = new StringMap();

        Gson gson = new Gson();
        JsonObject o = gson.fromJson(bufferedReader, JsonObject.class);

        if(o.isJsonObject()) {
            for(Map.Entry<String, JsonElement> entry : o.entrySet()) {
                String key = entry.getKey();
                JsonElement v = entry.getValue();

                if(v.isJsonPrimitive()) {
                    JsonPrimitive p = v.getAsJsonPrimitive();
                    if(p.isString()) map.add(key, p.getAsString());
                }

            }
        }

        return map;
    }

    @Override
    public void close() throws IOException {
        this.inputStream.close();
        this.streamReader.close();
        this.bufferedReader.close();
    }
}
