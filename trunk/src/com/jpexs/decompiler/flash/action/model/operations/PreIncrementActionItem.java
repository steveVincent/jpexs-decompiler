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
package com.jpexs.decompiler.flash.action.model.operations;

import com.jpexs.decompiler.flash.action.parser.script.ActionSourceGenerator;
import com.jpexs.decompiler.flash.action.swf4.ActionPush;
import com.jpexs.decompiler.flash.action.swf4.ActionSetProperty;
import com.jpexs.decompiler.flash.action.swf4.ActionSetVariable;
import com.jpexs.decompiler.flash.action.swf4.RegisterNumber;
import com.jpexs.decompiler.flash.action.swf5.ActionIncrement;
import com.jpexs.decompiler.flash.action.swf5.ActionSetMember;
import com.jpexs.decompiler.flash.action.swf5.ActionStoreRegister;
import com.jpexs.decompiler.flash.action.model.DirectValueActionItem;
import com.jpexs.decompiler.flash.action.model.GetMemberActionItem;
import com.jpexs.decompiler.flash.action.model.GetPropertyActionItem;
import com.jpexs.decompiler.flash.action.model.GetVariableActionItem;
import com.jpexs.decompiler.flash.ecma.*;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.SourceGenerator;
import com.jpexs.decompiler.graph.model.UnaryOpItem;
import java.util.ArrayList;
import java.util.List;

public class PreIncrementActionItem extends UnaryOpItem {

    public PreIncrementActionItem(GraphSourceItem instruction, GraphTargetItem object) {
        super(instruction, PRECEDENCE_UNARY, object, "++");
    }

    @Override
    public Object getResult() {
        return EcmaScript.toNumber(value.getResult()) + 1;
    }

    @Override
    public List<GraphSourceItem> toSource(List<Object> localData, SourceGenerator generator) {
        ActionSourceGenerator asGenerator = (ActionSourceGenerator) generator;
        List<GraphSourceItem> ret = new ArrayList<>();

        if (value instanceof GetVariableActionItem) {
            GetVariableActionItem gv = (GetVariableActionItem) value;
            ret.addAll(gv.toSource(localData, generator));
            ret.remove(ret.size() - 1); //ActionGetVariable
            ret.addAll(gv.toSource(localData, generator));
            ret.add(new ActionIncrement());
            int tmpReg = asGenerator.getTempRegister(localData);
            ret.add(new ActionStoreRegister(tmpReg));
            ret.add(new ActionSetVariable());
            ret.add(new ActionPush(new RegisterNumber(tmpReg)));
        } else if (value instanceof GetMemberActionItem) {
            GetMemberActionItem mem = (GetMemberActionItem) value;
            ret.addAll(mem.toSource(localData, generator));
            ret.remove(ret.size() - 1); //ActionGetMember
            ret.addAll(mem.toSource(localData, generator));
            ret.add(new ActionIncrement());
            int tmpReg = asGenerator.getTempRegister(localData);
            ret.add(new ActionStoreRegister(tmpReg));
            ret.add(new ActionSetMember());
            ret.add(new ActionPush(new RegisterNumber(tmpReg)));
        } else if ((value instanceof DirectValueActionItem) && ((DirectValueActionItem) value).value instanceof RegisterNumber) {
            RegisterNumber rn = (RegisterNumber) ((DirectValueActionItem) value).value;
            ret.add(new ActionPush(new RegisterNumber(rn.number)));
            ret.add(new ActionIncrement());
            ret.add(new ActionStoreRegister(rn.number));
        } else if (value instanceof GetPropertyActionItem) {
            GetPropertyActionItem gp = (GetPropertyActionItem) value;
            ret.addAll(gp.toSource(localData, generator)); // old value
            ret.addAll(gp.toSource(localData, generator));
            ret.remove(ret.size() - 1);
            ret.addAll(gp.toSource(localData, generator));
            ret.add(new ActionIncrement());
            ret.add(new ActionSetProperty());
        }
        return ret;
    }
}