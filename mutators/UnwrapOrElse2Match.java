package org.trust.mutators;

import org.treesitter.TSNode;

import java.util.HashMap;

public class UnwrapOrElse2Match extends Mutator {
    @Override
    public HashMap<int[], String> searchCandidates(TSNode node, String srcCode) {
        HashMap<int[], String> edits = new HashMap<>();

        // 查找unwrap_or_else表达式
        if (node.getType().equals("call_expression")) {
            TSNode functionNode = node.getChildByFieldName("function");
            if (!functionNode.isNull()) {
                String functionName = srcCode.substring(functionNode.getStartByte(), functionNode.getEndByte());
                if (functionName.endsWith(".unwrap_or_else")) {
                    // 获取unwrap_or_else的参数
                    TSNode argumentsNode = node.getChildByFieldName("arguments");
                    if (!argumentsNode.isNull() && argumentsNode.getNamedChildCount() == 1) {
                        TSNode closureNode = argumentsNode.getNamedChild(0);
                        if (closureNode.getType().equals("closure_expression")) {
                            // 获取闭包体
                            TSNode closureBody = closureNode.getChildByFieldName("body");
                            if (!closureBody.isNull()) {
                                // 获取unwrap_or_else的目标值
                                String targetValue = srcCode.substring(functionNode.getStartByte(), functionNode.getEndByte()).replace(".unwrap_or_else", "");
                                String closureCode = srcCode.substring(closureBody.getStartByte(), closureBody.getEndByte());

                                // 构建match表达式
                                String matchExpr = "match " + targetValue + " {\n" +
                                        "    Some(val) => val,\n" +
                                        "    None => " + closureCode + ",\n" +
                                        "}";

                                // 添加编辑
                                int[] location = new int[]{node.getStartByte(), node.getEndByte()};
                                edits.put(location, matchExpr);
                            }
                        }
                    }
                }
            }
        }

        // 递归查找子节点
        for (int i = 0; i < node.getChildCount(); i++) {
            edits.putAll(searchCandidates(node.getChild(i), srcCode));
        }

        return edits;
    }
}
