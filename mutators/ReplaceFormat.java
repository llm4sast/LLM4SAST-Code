package org.trust.mutators;

import org.treesitter.TSNode;

import java.util.HashMap;

public class ReplaceFormat extends Mutator {
    @Override
    public HashMap<int[], String> searchCandidates(TSNode node, String srcCode) {
        HashMap<int[], String> edits = new HashMap<>();
        if (node.getType().equals("macro_invocation")) {
            TSNode macro_identifier = node.getChildByFieldName("macro");
            String macroCode = srcCode.substring(macro_identifier.getStartByte(), macro_identifier.getEndByte());
            if (macroCode.equals("format")) {
                int[] location = new int[]{node.getStartByte(), node.getEndByte()};
                String originalCode = srcCode.substring(node.getStartByte(), node.getEndByte());
                String replaceCode = "std::fmt::format(format_args!" + originalCode.substring("format!".length()) + ")";
                edits.put(location, replaceCode);
            }
        }
        for (int i = 0; i < node.getNamedChildCount(); i++) {
            edits.putAll(searchCandidates(node.getNamedChild(i), srcCode));
        }
        return edits;
    }
}