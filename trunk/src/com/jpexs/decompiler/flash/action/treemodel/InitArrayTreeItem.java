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
package com.jpexs.decompiler.flash.action.treemodel;

import com.jpexs.decompiler.flash.action.Action;
import com.jpexs.decompiler.flash.action.IgnoredPair;
import java.util.List;

public class InitArrayTreeItem extends TreeItem {

   public List<TreeItem> values;

   public InitArrayTreeItem(Action instruction, List<TreeItem> values) {
      super(instruction, PRECEDENCE_PRIMARY);
      this.values = values;
   }

   @Override
   public String toString(ConstantPool constants) {
      String arrStr = "";
      for (int i = 0; i < values.size(); i++) {
         if (i > 0) {
            arrStr += hilight(",");
         }
         arrStr += values.get(i).toString(constants);
      }
      return hilight("[") + arrStr + hilight("]");
   }

   @Override
   public List<com.jpexs.decompiler.flash.action.IgnoredPair> getNeededActions() {
      List<IgnoredPair> ret = super.getNeededActions();
      for (TreeItem value : values) {
         ret.addAll(value.getNeededActions());
      }
      return ret;
   }
}