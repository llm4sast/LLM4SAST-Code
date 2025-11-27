package org.trust.mutators;

import org.treesitter.TSNode;

import java.util.HashMap;

public class AndThen2NestedMatch extends Mutator {
    @Override
    public HashMap<int[], String> searchCandidates(TSNode node, String srcCode) {
        HashMap<int[], String> edits = new HashMap<>();

        // 查找and_then表达式
        if (node.getType().equals("call_expression")) {
            TSNode functionNode = node.getChildByFieldName("function");
            if (!functionNode.isNull()) {
                String functionName = srcCode.substring(functionNode.getStartByte(), functionNode.getEndByte());
                if (functionName.endsWith(".and_then")) {
                    // 获取and_then的参数
                    TSNode argumentsNode = node.getChildByFieldName("arguments");
                    if (!argumentsNode.isNull() && argumentsNode.getNamedChildCount() == 1) {

                        TSNode argNode = argumentsNode.getNamedChild(0);
                        String argCode = srcCode.substring(argNode.getStartByte(), argNode.getEndByte());
                        
                        if (argNode.getType().equals("closure_expression")) {
                            // 处理闭包情况
                            TSNode closureBody = argNode.getChildByFieldName("body");
                            if (!closureBody.isNull()) {
                                // 获取闭包参数名
                                TSNode parameters = argNode.getChildByFieldName("parameters");
                                String paramName = "val"; // 默认值
                                if (!parameters.isNull() && parameters.getNamedChildCount() > 0) {
                                    TSNode param = parameters.getNamedChild(0);
                                    paramName = srcCode.substring(param.getStartByte(), param.getEndByte());
                                }

                                // 获取and_then的目标值
                                String targetValue = srcCode.substring(functionNode.getStartByte(), functionNode.getEndByte()).replace(".and_then", "");
                                String closureCode = srcCode.substring(closureBody.getStartByte(), closureBody.getEndByte());

                                // 构建嵌套match表达式
                                String matchExpr = "match " + targetValue + " {\n" +
                                        "    Some(" + paramName + ") => match " + closureCode.replace(paramName, paramName) + " {\n" +
                                        "        Some(" + paramName + ") => Some(" + paramName + "),\n" +
                                        "        None => None,\n" +
                                        "    },\n" +
                                        "    None => None,\n" +
                                        "}";

                                // 添加编辑
                                int[] location = new int[]{node.getStartByte(), node.getEndByte()};
                                edits.put(location, matchExpr);
                            }
                        } else if (argNode.getType().equals("identifier") || argNode.getType().equals("call_expression")) {
                            // 处理函数名或函数调用情况
                            String targetValue = srcCode.substring(functionNode.getStartByte(), functionNode.getEndByte()).replace(".and_then", "");
                            
                            // 构建嵌套match表达式
                            String matchExpr = "match " + targetValue + " {\n" +
                                    "    Some(val) => match " + argCode + "(val) {\n" +
                                    "        Some(val) => Some(val),\n" +
                                    "        None => None,\n" +
                                    "    },\n" +
                                    "    None => None,\n" +
                                    "}";

                            // 添加编辑
                            int[] location = new int[]{node.getStartByte(), node.getEndByte()};
                            edits.put(location, matchExpr);
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
