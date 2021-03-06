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
package com.jpexs.decompiler.flash.types;

import com.jpexs.decompiler.flash.types.annotations.SWFType;
import java.io.Serializable;

/**
 *
 * @author JPEXS
 */
public class GRADIENT implements Serializable {

    /**
     * Spread mode
     */
    @SWFType(value = BasicType.UB, count = 2)
    public int spreadMode;
    public static final int SPREAD_PAD_MODE = 0;
    public static final int SPREAD_REFLECT_MODE = 1;
    public static final int SPREAD_REPEAT_MODE = 2;
    public static final int SPREAD_RESERVED = 3;
    /**
     * Interpolation mode
     */
    @SWFType(value = BasicType.UB, count = 2)
    public int interpolationMode;
    public static final int INTERPOLATION_RGB_MODE = 0;
    public static final int INTERPOLATION_LINEAR_RGB_MODE = 1;
    public static final int INTERPOLATION_RESERVED1 = 2;
    public static final int INTERPOLATION_RESERVED2 = 3;
    public GRADRECORD[] gradientRecords = new GRADRECORD[0];
}
