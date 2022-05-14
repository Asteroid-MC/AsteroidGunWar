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

import java.io.File;
import java.io.IOException;

public class XRL {

    private String path;
    private File file;
    private boolean directory;

    public XRL(boolean directory, String... path) {
        this.path = String.join(File.separator, path);
        this.file = new File(this.path);
        this.directory = directory;
    }

    public String getPath() {
        return path;
    }

    public boolean isDirectory() {
        return directory;
    }

    public File toFile() {
        return file;
    }

    public void append(XRL xrl) {
        if(directory) {
            XRL x = append0(this, xrl);
            if(x == null) return;

            this.path = x.path;
            this.directory = x.directory;
            this.file = x.file;
        }
    }

    public void appendTo(XRL xrl) {
        if(xrl.directory) {
            XRL x = append0(xrl, this);
            if(x == null) return;

            this.path = x.path;
            this.file = x.file;
        }
    }

    private XRL append0(XRL xrl0, XRL xrl1) {
        if(xrl0.directory) {
            String path = "";
            if(xrl0.path.endsWith(File.separator)) {
                if(xrl1.path.startsWith(File.separator)) {
                    int len = File.separator.length();
                    path += xrl1.path.substring(len);
                } else {
                    path += xrl1.path;
                }
            } else {
                if(xrl1.path.startsWith(File.separator)) {
                    path += xrl1.path;
                } else {
                    path += File.separator + xrl1.path;
                }
            }
            return new XRL(xrl1.directory, path);
        }
        return null;
    }

    public boolean create() throws IOException {
        if(directory) {
            return file.mkdirs();
        } else {
            return file.createNewFile();
        }
    }

}
