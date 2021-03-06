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

import com.jpexs.decompiler.flash.tags.base.NeedsCharacters;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author JPEXS
 */
public class LINESTYLEARRAY implements NeedsCharacters, Serializable {

    public LINESTYLE[] lineStyles = new LINESTYLE[0];

    @Override
    public void getNeededCharacters(Set<Integer> needed) {
        for (LINESTYLE ls : lineStyles) {
            ls.getNeededCharacters(needed);
        }
    }

    @Override
    public boolean removeCharacter(int characterId) {
        boolean modified = false;
        for (LINESTYLE ls : lineStyles) {
            modified |= ls.removeCharacter(characterId);
        }
        return modified;
    }
}
