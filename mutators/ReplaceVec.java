package org.trust.mutators;

import org.treesitter.TSNode;

import java.util.HashMap;

public class ReplaceVec extends Mutator {
    @Override
    public HashMap<int[], String> searchCandidates(TSNode node, String srcCode) {
        HashMap<int[], String> edits = new HashMap<>();
        if (node.getType().equals("macro_invocation")) {
            TSNode macro_identifier = node.getChildByFieldName("macro");
            String macroCode = srcCode.substring(macro_identifier.getStartByte(), macro_identifier.getEndByte());
            if (macroCode.equals("vec")) {
                int[] location = new int[]{node.getStartByte(), node.getEndByte()};
                String originalCode = srcCode.substring(node.getStartByte(), node.getEndByte());
                String replaceCode = "";
                
                // 处理 vec![] 情况
                if (originalCode.equals("vec![]")) {
                    replaceCode = "Vec::new()";
                }
                // 处理 vec![x] 情况
                else if (originalCode.matches("vec!\\[[^;]+\\]")) {
                    String content = originalCode.substring("vec![".length(), originalCode.length() - 1);
                    replaceCode = "[" + content + "].to_vec()";
                }
                // 处理 vec![x; y] 情况
                else if (originalCode.matches("vec!\\[[^;]+;[^\\]]+\\]")) {
                    String[] parts = originalCode.substring("vec![".length(), originalCode.length() - 1).split(";");
                    replaceCode = "std::vec::from_elem(" + parts[0].trim() + ", " + parts[1].trim() + ")";
                }
                
                if (!replaceCode.isEmpty()) {
                    edits.put(location, replaceCode);
                }
            }
        }
        for (int i = 0; i < node.getNamedChildCount(); i++) {
            edits.putAll(searchCandidates(node.getNamedChild(i), srcCode));
        }
        return edits;
    }
}