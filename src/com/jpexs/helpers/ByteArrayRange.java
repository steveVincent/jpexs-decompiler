/*
 *  Copyright (C) 2010-2014 JPEXS
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jpexs.helpers;

/**
 *
 * @author JPEXS
 */
public class ByteArrayRange {

    public final byte[] array;
    public final int pos;
    public final int length;

    public ByteArrayRange() {
        this(new byte[0]);
    }

    public ByteArrayRange(byte[] array) {
        this.array = array;
        this.pos = 0;
        this.length = array.length;
    }

    public ByteArrayRange(byte[] array, int pos, int length) {
        this.array = array;
        this.pos = pos;
        this.length = length;
    }

    public byte[] getRangeData() {
        byte[] data = new byte[length];
        System.arraycopy(array, pos, data, 0, length);
        return data;
    }
}
