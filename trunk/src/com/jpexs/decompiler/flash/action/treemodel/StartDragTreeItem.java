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
import java.util.List;

public class StartDragTreeItem extends TreeItem {

   public TreeItem target;
   public TreeItem lockCenter;
   public TreeItem constrain;
   public TreeItem y2;
   public TreeItem x2;
   public TreeItem y1;
   public TreeItem x1;

   public StartDragTreeItem(Action instruction, TreeItem target, TreeItem lockCenter, TreeItem constrain, TreeItem x1, TreeItem y1, TreeItem x2, TreeItem y2) {
      super(instruction, PRECEDENCE_PRIMARY);
      this.target = target;
      this.lockCenter = lockCenter;
      this.constrain = constrain;
      this.y2 = y2;
      this.x2 = x2;
      this.y1 = y1;
      this.x1 = x1;
   }

   @Override
   public String toString(ConstantPool constants) {
      boolean hasConstrains = true;
      if (constrain instanceof DirectValueTreeItem) {
         if (((DirectValueTreeItem) constrain).value instanceof Long) {
            if (((long) (Long) ((DirectValueTreeItem) constrain).value) == 0) {
               hasConstrains = false;
            }
         }
         if (((DirectValueTreeItem) constrain).value instanceof Boolean) {
            if (((boolean) (Boolean) ((DirectValueTreeItem) constrain).value) == false) {
               hasConstrains = false;
            }
         }
      }
      return hilight("startDrag(") + target.toString(constants) + hilight(",") + lockCenter.toString(constants) + (hasConstrains ? hilight(",") + x1.toString(constants) + hilight(",") + y1.toString(constants) + hilight(",") + x2.toString(constants) + hilight(",") + y2.toString(constants) : "") + hilight(")");
   }

   @Override
   public List<com.jpexs.decompiler.flash.action.IgnoredPair> getNeededActions() {
      List<com.jpexs.decompiler.flash.action.IgnoredPair> ret = super.getNeededActions();
      ret.addAll(target.getNeededActions());
      ret.addAll(constrain.getNeededActions());
      ret.addAll(x1.getNeededActions());
      ret.addAll(x2.getNeededActions());
      ret.addAll(y1.getNeededActions());
      ret.addAll(y2.getNeededActions());
      return ret;
   }
}