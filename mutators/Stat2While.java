package org.trust.mutators;


import org.treesitter.TSNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stat2While extends Mutator {
    @Override
    public HashMap<int[], String> searchCandidates(TSNode node, String srcCode) {
        HashMap<int[], String> edits = new HashMap<>();
        if (node.getType().equals("if_expression")) {
            // 检查if语句前是否有let赋值语句，以及是否为else if的情况
            TSNode parent = node.getParent();
            if (parent != null && !parent.getType().equals("let_declaration") && !parent.getType().equals("else_clause")) {
                // 检查if内的block和else内的block是否包含continue或break语句
                // 检查if内的block和else内的block的最后一个子节点是否是expression_statement
                TSNode ifBlockNode = node.getChildByFieldName("consequence");
                TSNode elseBlockNode = node.getChildByFieldName("alternative");
                boolean containsContinueOrBreak = false;
                boolean lastNodeIsExpressionStatement = true;

                if (ifBlockNode != null && ifBlockNode.getNamedChildCount() > 0) {
                    String ifBlockCode = srcCode.substring(ifBlockNode.getStartByte(), ifBlockNode.getEndByte());
                    if (ifBlockCode.contains("continue;") || ifBlockCode.contains("break;")) {
                        containsContinueOrBreak = true;
                    }

                    TSNode lastIfChild = ifBlockNode.getNamedChild(ifBlockNode.getNamedChildCount() - 1);
                    if (!lastIfChild.getType().equals("expression_statement")) {
                        lastNodeIsExpressionStatement = false;
                    }
                }

                if (!elseBlockNode.isNull() && elseBlockNode.getNamedChildCount() > 0) {
                    String elseBlockCode = srcCode.substring(elseBlockNode.getStartByte(), elseBlockNode.getEndByte());
                    if (elseBlockCode.contains("continue;") || elseBlockCode.contains("break;")) {
                        containsContinueOrBreak = true;
                    }

                    TSNode lastElseChild = elseBlockNode.getNamedChild(elseBlockNode.getNamedChildCount() - 1);
                    if (!lastElseChild.getType().equals("expression_statement")) {
                        lastNodeIsExpressionStatement = false;
                    }
                }

                if (lastNodeIsExpressionStatement && !containsContinueOrBreak) {
                    int[] location = new int[]{node.getStartByte(), node.getEndByte()};
                    String replaceCode = "while {" + srcCode.substring(node.getStartByte(), node.getEndByte()) + "true} {break}";
                    edits.put(location, replaceCode);
                }
            }
        }
        for (int i = 0; i < node.getChildCount(); i++) {
            edits.putAll(searchCandidates(node.getChild(i), srcCode));
        }
        return edits;
    }


}
