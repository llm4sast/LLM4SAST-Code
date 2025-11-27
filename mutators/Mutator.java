package org.trust.mutators;

import org.treesitter.TSNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mutator {
    public static void printAST(TSNode node, String src_code, int indent) {
        StringBuilder indentation = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            indentation.append("  ");
        }
        System.out.println(indentation + node.getType() + " [" + node.getStartPoint().getRow() + ", " + node.getStartPoint().getRow() + "] - [" + node.getEndPoint().getRow() + ", " + node.getEndPoint().getColumn() + "]");
        for (int i = 0; i < node.getNamedChildCount(); i++) {
            TSNode child = node.getNamedChild(i);
            printAST(child, src_code, indent + 1);
        }
    }

    public List<String> mutate(TSNode node, String srcCode) {
        HashMap<int[], String> edits = searchCandidates(node, srcCode);
        List<String> mutantCodes = new ArrayList<>();
        for (Map.Entry<int[], String> edit : edits.entrySet()) {
            String mutantCode = srcCode.substring(0, edit.getKey()[0]) + edit.getValue() + srcCode.substring(edit.getKey()[1]);
            mutantCodes.add(mutantCode);
        }
        return mutantCodes;
    }

    public HashMap<int[], String> searchCandidates(TSNode node, String srcCode) {
        return null;
    }
    public static boolean isConstFunction(TSNode node, String srcCode) {
        // 检查函数是否为const函数
        boolean isConstFunction = false;
        for (int i = 0; i < node.getNamedChildCount(); i++) {
            TSNode child = node.getNamedChild(i);
            if (child.getType().equals("function_modifiers")) {
                if (srcCode.substring(child.getStartByte(), child.getEndByte()).contains("const"))
                    isConstFunction = true;
                break;
            }
        }
        return isConstFunction;
    }
    public List<int[]> findFunctionCalls(TSNode rootNode, String functionName, String code) {
        List<int[]> callSites = new ArrayList<>();
        searchFunctionCalls(rootNode, functionName, callSites, code);
        return callSites;
    }

    public void searchFunctionCalls(TSNode node, String functionName, List<int[]> callSites, String code) {
        if (node.getType().equals("call_expression")) {
            TSNode function = node.getChildByFieldName("function");
            if (!function.isNull() && code.substring(function.getStartByte(), function.getEndByte()).equals(functionName)) {
                callSites.add(new int[]{node.getStartByte(), node.getEndByte()});
            }
        }

        for (int i = 0; i < node.getNamedChildCount(); i++) {
            searchFunctionCalls(node.getNamedChild(i), functionName, callSites, code);
        }
    }
}
