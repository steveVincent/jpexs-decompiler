/*
 *  Copyright (C) 2010-2013 JPEXS
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
package com.jpexs.decompiler.flash.abc.avm2.instructions.stack;

import com.jpexs.decompiler.flash.abc.ABC;
import com.jpexs.decompiler.flash.abc.avm2.AVM2Code;
import com.jpexs.decompiler.flash.abc.avm2.ConstantPool;
import com.jpexs.decompiler.flash.abc.avm2.instructions.AVM2Instruction;
import com.jpexs.decompiler.flash.abc.avm2.instructions.InstructionDefinition;
import com.jpexs.decompiler.flash.abc.avm2.treemodel.NameSpaceTreeItem;
import com.jpexs.decompiler.flash.abc.avm2.treemodel.TreeItem;
import com.jpexs.decompiler.flash.abc.types.MethodBody;
import com.jpexs.decompiler.flash.abc.types.MethodInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class PushNamespaceIns extends InstructionDefinition {

   public PushNamespaceIns() {
      super(0x31, "pushnamespace", new int[]{AVM2Code.OPT_U30});
   }

   @Override
   public void translate(boolean isStatic, int classIndex, java.util.HashMap<Integer, TreeItem> localRegs, Stack<TreeItem> stack, Stack<TreeItem> scopeStack, ConstantPool constants, AVM2Instruction ins, MethodInfo[] method_info, List<TreeItem> output, MethodBody body, ABC abc, HashMap<Integer, String> localRegNames, List<String> fullyQualifiedNames) {
      stack.push(new NameSpaceTreeItem(ins, ins.operands[0]));
   }

   @Override
   public int getStackDelta(AVM2Instruction ins, ABC abc) {
      return 1;
   }
}