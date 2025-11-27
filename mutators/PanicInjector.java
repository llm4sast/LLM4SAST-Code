package org.trust.mutators;

import org.treesitter.TSNode;

import java.util.HashMap;

public class PanicInjector extends Mutator {
    @Override
    public HashMap<int[], String> searchCandidates(TSNode node, String srcCode) {
        HashMap<int[], String> edits = new HashMap<>();
        // 检查当前节点是否是语句节点
        if (node.getType().equals("expression_statement") || 
            node.getType().equals("let_declaration")){

            // 在语句后插入panic宏
            int insertPosition = node.getEndByte();
            String panicStmt = ";\n    panic!(\"injected panic\");";
            edits.put(new int[]{insertPosition, insertPosition}, panicStmt);
        }

        // 递归处理子节点
        for (int i = 0; i < node.getNamedChildCount(); i++) {
            edits.putAll(searchCandidates(node.getNamedChild(i), srcCode));
        }

        return edits;
    }
}