package org.trust.mutators;

import org.treesitter.TSNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClosureWrapper extends Mutator {
    @Override
    public HashMap<int[], String> searchCandidates(TSNode node, String srcCode) {
        HashMap<int[], String> edits = new HashMap<>();
        if (node.getType().equals("function_item")) {
            boolean isConstFunction = isConstFunction(node, srcCode);

            if (!isConstFunction) {
                TSNode nameNode = node.getChildByFieldName("name");
                String functionName = srcCode.substring(nameNode.getStartByte(), nameNode.getEndByte());

                TSNode bodyNode = node.getChildByFieldName("body");
                int[] location = new int[]{bodyNode.getStartByte(), bodyNode.getEndByte()};
                String replaceCode = "{\n    (|| " + srcCode.substring(location[0], location[1]) + ")()\n}";
                edits.put(location, replaceCode);
            }
        }
        for (int i = 0; i < node.getNamedChildCount(); i++) {
            edits.putAll(searchCandidates(node.getNamedChild(i), srcCode));
        }
        return edits;
    }



}