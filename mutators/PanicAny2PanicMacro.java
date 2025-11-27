package org.trust.mutators;

import org.treesitter.TSNode;

import java.util.HashMap;

public class PanicAny2PanicMacro extends Mutator {
    @Override
    public HashMap<int[], String> searchCandidates(TSNode node, String srcCode) {
        HashMap<int[], String> edits = new HashMap<>();
        if (node.getType().equals("call_expression")) {
            TSNode functionNode = node.getChildByFieldName("function");
            if (!functionNode.isNull()) {
                String functionName = srcCode.substring(functionNode.getStartByte(), functionNode.getEndByte());
                if (functionName.equals("std::panic::panic_any") || functionName.equals("panic_any")) {
                    TSNode argumentsNode = node.getChildByFieldName("arguments");
                    if (!argumentsNode.isNull()) {
                        int[] location = new int[]{node.getStartByte(), node.getEndByte()};
                        if (argumentsNode.getNamedChildCount() == 0) {
                            String replaceCode = "panic!()";
                            edits.put(location, replaceCode);
                        } else {
                            String argCode = srcCode.substring(argumentsNode.getStartByte(), argumentsNode.getEndByte());
                            String replaceCode = "panic!" + argCode;
                            edits.put(location, replaceCode);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < node.getNamedChildCount(); i++) {
            edits.putAll(searchCandidates(node.getNamedChild(i), srcCode));
        }
        return edits;
    }
}