/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.json.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Java5Compat {

    public static <E> Set<E> newSetFromMap(Map<E, Boolean> map) {
        return new SetFromMap<E>(map);
    }

    private static class SetFromMap<E> extends AbstractSet<E>
            implements Set<E>, Serializable
    {
        private final Map<E, Boolean> m;  // The backing map
        private transient Set<E> s;       // Its keySet

        SetFromMap(Map<E, Boolean> map) {
            if (!map.isEmpty())
                throw new IllegalArgumentException("Map is non-empty");
            m = map;
            s = map.keySet();
        }

        public void clear()               {        m.clear(); }
        public int size()                 { return m.size(); }
        public boolean isEmpty()          { return m.isEmpty(); }
        public boolean contains(Object o) { return m.containsKey(o); }
        public boolean remove(Object o)   { return m.remove(o) != null; }
        public boolean add(E e) { return m.put(e, Boolean.TRUE) == null; }
        public Iterator<E> iterator()     { return s.iterator(); }
        public Object[] toArray()         { return s.toArray(); }
        public <T> T[] toArray(T[] a)     { return s.toArray(a); }
        public String toString()          { return s.toString(); }
        public int hashCode()             { return s.hashCode(); }
        public boolean equals(Object o)   { return o == this || s.equals(o); }
        public boolean containsAll(Collection<?> c) {return s.containsAll(c);}
        public boolean removeAll(Collection<?> c)   {return s.removeAll(c);}
        public boolean retainAll(Collection<?> c)   {return s.retainAll(c);}

        private static final long serialVersionUID = 2454657854757543876L;

        private void readObject(java.io.ObjectInputStream stream)
                throws IOException, ClassNotFoundException
        {
            stream.defaultReadObject();
            s = m.keySet();
        }
    }
}
