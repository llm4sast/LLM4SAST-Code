package org.trust.mutators;

import org.treesitter.TSNode;

import java.util.HashMap;

public class UnsafeWrapper extends Mutator {
    @Override
    public HashMap<int[], String> searchCandidates(TSNode node, String srcCode) {
        HashMap<int[], String> edits = new HashMap<>();
        if (node.getType().equals("function_item")) {
            TSNode bodyNode = node.getChildByFieldName("body");
            int[] location = new int[]{bodyNode.getStartByte(), bodyNode.getEndByte()};
            String replaceCode = "{\n    unsafe" + srcCode.substring(location[0], location[1]) + "\n}";
            edits.put(location, replaceCode);
        }
        for (int i = 0; i < node.getNamedChildCount(); i++) {
            edits.putAll(searchCandidates(node.getNamedChild(i), srcCode));
        }
        return edits;
    }
}