/*
 *  Copyright (C) 2010-2011 JPEXS
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

package com.jpexs.asdec.abc.avm2.treemodel;

import com.jpexs.asdec.abc.avm2.ConstantPool; import java.util.HashMap;
import com.jpexs.asdec.abc.avm2.instructions.AVM2Instruction;


public class InTreeItem extends TreeItem {
    public TreeItem object;
    public TreeItem collection;

    public InTreeItem(AVM2Instruction instruction, TreeItem object, TreeItem collection) {
        super(instruction, NOPRECEDENCE);
        this.object = object;
        this.collection = collection;
    }

    @Override
    public String toString(ConstantPool constants, HashMap<Integer,String> localRegNames) {
        return  object.toString(constants,localRegNames) + hilight(" in ") + collection.toString(constants,localRegNames);
    }


}