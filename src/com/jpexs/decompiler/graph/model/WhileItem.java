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
package com.jpexs.decompiler.graph.model;

import com.jpexs.decompiler.flash.SourceGeneratorLocalData;
import com.jpexs.decompiler.flash.helpers.GraphTextWriter;
import com.jpexs.decompiler.flash.helpers.LoopWithType;
import com.jpexs.decompiler.flash.helpers.NulWriter;
import com.jpexs.decompiler.graph.Block;
import com.jpexs.decompiler.graph.CompilationException;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.Loop;
import com.jpexs.decompiler.graph.SourceGenerator;
import com.jpexs.decompiler.graph.TypeItem;
import java.util.ArrayList;
import java.util.List;

public class WhileItem extends LoopItem implements Block {

    public List<GraphTargetItem> expression;
    public List<GraphTargetItem> commands;
    private boolean labelUsed;

    @Override
    public List<List<GraphTargetItem>> getSubs() {
        List<List<GraphTargetItem>> ret = new ArrayList<>();
        if (commands != null) {
            ret.add(commands);
        }
        return ret;
    }

    public WhileItem(GraphSourceItem src, Loop loop, List<GraphTargetItem> expression, List<GraphTargetItem> commands) {
        super(src, loop);
        this.expression = expression;
        this.commands = commands;
    }

    @Override
    public GraphTextWriter appendTo(GraphTextWriter writer, LocalData localData) throws InterruptedException {
        if (writer instanceof NulWriter) {
            ((NulWriter) writer).startLoop(loop.id, LoopWithType.LOOP_TYPE_LOOP);
        }
        if (labelUsed) {
            writer.append("loop" + loop.id + ":").newLine();
        }
        writer.append("while");
        if (writer.getFormatting().spaceBeforeParenthesesWhileParentheses) {
            writer.append(" ");
        }
        writer.append("(");
        for (int i = 0; i < expression.size(); i++) {
            if (expression.get(i).isEmpty()) {
                continue;
            }
            if (i != 0) {
                writer.append(", ");
            }
            expression.get(i).toString(writer, localData);
        }
        writer.append(")");
        writer.startBlock();
        for (GraphTargetItem ti : commands) {
            if (!ti.isEmpty()) {
                ti.toStringSemicoloned(writer, localData).newLine();
            }
        }
        writer.endBlock();
        if (writer instanceof NulWriter) {
            LoopWithType loopOjb = ((NulWriter) writer).endLoop(loop.id);
            labelUsed = loopOjb.used;
        }
        return writer;
    }

    @Override
    public List<ContinueItem> getContinues() {
        List<ContinueItem> ret = new ArrayList<>();
        for (GraphTargetItem ti : commands) {
            if (ti instanceof ContinueItem) {
                ret.add((ContinueItem) ti);
            }
            if (ti instanceof Block) {
                ret.addAll(((Block) ti).getContinues());
            }
        }
        return ret;
    }

    @Override
    public boolean needsSemicolon() {
        return false;
    }

    @Override
    public List<GraphSourceItem> toSource(SourceGeneratorLocalData localData, SourceGenerator generator) throws CompilationException {
        return generator.generate(localData, this);
    }

    @Override
    public boolean hasReturnValue() {
        return false;
    }

    @Override
    public GraphTargetItem returnType() {
        return TypeItem.UNBOUNDED;
    }
}
