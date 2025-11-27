package org.trust.mutators;

import org.treesitter.TSNode;

import java.util.HashMap;

public class QuestionMark2Match extends Mutator {
    @Override
    public HashMap<int[], String> searchCandidates(TSNode node, String srcCode) {
        HashMap<int[], String> edits = new HashMap<>();
        // 查找问号运算符
        if (node.getType().equals("try_expression")) {
            // 获取try表达式的内容
            TSNode exprNode = node.getChild(0);
            if (!exprNode.isNull()) {
                // 获取表达式代码
                String expr = srcCode.substring(exprNode.getStartByte(), exprNode.getEndByte());
                
                // 判断表达式类型
                // 通过函数签名判断表达式类型
                String exprType = "Unknown";
                TSNode parent = exprNode.getParent();
                while (parent != null) {
                    if (parent.getType().equals("function_item")) {
                        TSNode returnType = parent.getChildByFieldName("return_type");
                        if (!returnType.isNull()) {
                            String returnTypeStr = srcCode.substring(returnType.getStartByte(), returnType.getEndByte());
                            if (returnTypeStr.contains("Result")) {
                                exprType = "Result";
                            } else if (returnTypeStr.contains("Option")) {
                                exprType = "Option";
                            }
                        }
                        break;
                    }
                    parent = parent.getParent();
                }
                
                // 根据类型构建不同的match表达式
                String matchExpr;
                if (exprType.equals("Result")) {
                    matchExpr = "match " + expr + " {\n" +
                            "    Ok(val) => val,\n" +
                            "    Err(e) => return Err(e.into()),\n" +
                            "}";
                } else if (exprType.equals("Option")) {
                    matchExpr = "match " + expr + " {\n" +
                            "    Some(val) => val,\n" +
                            "    None => return None,\n" +
                            "}";
                } else {
                    // 未知类型，不进行处理
                    return edits;
                }
                
                // 添加编辑
                int[] location = new int[]{node.getStartByte(), node.getEndByte()};
                edits.put(location, matchExpr);
            }
        }

        // 递归查找子节点
        for (int i = 0; i < node.getChildCount(); i++) {
            edits.putAll(searchCandidates(node.getChild(i), srcCode));
        }

        return edits;
    }
}
