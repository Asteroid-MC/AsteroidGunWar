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

import io.github.asteroidmc.agw.command.SenderType;
import io.github.asteroidmc.agw.localization.AgwLanguage;
import io.github.asteroidmc.agw.localization.UnlocalizedText;
import org.bukkit.ChatColor;

import java.util.*;

public final class AgwComponent {

    private final List<TextComponent<?>> components = new ArrayList<>();
    private final Map<String, Object> args = new HashMap<>();
    private String str;

    public AgwComponent append(Object... objects) {
        for(Object o : objects) {
            if(o == null) continue;
            try {
                if (o instanceof TextComponent) {
                    this.components.add((TextComponent<?>) o);
                    continue;
                }
                if (o instanceof AgwComponent) {
                    List<TextComponent<?>> components = new ArrayList<>(((AgwComponent) o).components());
                    this.components.addAll(components);
                    continue;
                }
                if (o instanceof Number) {
                    this.components.add(new NumberComponent((Number) o));
                    continue;
                }
                if (o instanceof String) {
                    if(((String) o).isEmpty()) continue;
                    this.components.add(new StringComponent((String) o));
                    continue;
                }
                if (o instanceof ChatColor) {
                    this.components.add(new ColorComponent((ChatColor) o));
                    continue;
                }
                if (o instanceof UnlocalizedText) {
                    this.components.add(new InternationalComponent((UnlocalizedText) o));
                    continue;
                }
                this.components.add(new StringComponent(String.valueOf(o)));
            } catch(Throwable t) {
                t.printStackTrace();
            }
        }
        return this;
    }

    public void setArgument(String arg, Object val) {
        if(arg == null && val == null) return;
        args.put(arg, val);
    }

    public String stringify(AgwLanguage lang) {
        StringBuilder sb = new StringBuilder();
        for(TextComponent<?> c : components) {
            if(c instanceof InternationalComponent) {
                ((InternationalComponent) c).format(lang);
            }
            c.applyColor();
            for(Map.Entry<String, Object> entry : args.entrySet()) {
                Object o = entry.getValue();
                String str;
                if(o instanceof InternationalComponent) {
                    InternationalComponent ic = ((InternationalComponent) o);
                    ic.format(lang);
                    str = ic.toString();
                } else if(o instanceof UnlocalizedText) {
                    UnlocalizedText ut = (UnlocalizedText) o;
                    str = ut.localizer().format(lang);
                } else if(o instanceof SenderType) {
                    SenderType st = (SenderType) o;
                    str = st.getText().localizer().format(lang);
                } else {
                    str = o.toString();
                }

                c.replaceArgument(entry.getKey(), str);
            }
            sb.append(c);
        }
        this.str = sb.toString().trim();
        return this.str;
    }

    public String toString() {
        return str;
    }

    public Collection<TextComponent<?>> components() {
        return components;
    }

    public TextComponent<?> getComponent(int index) {
        return components.get(index);
    }

    public int size() {
        return components.size();
    }

}
