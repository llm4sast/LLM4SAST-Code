package org.trust.mutators;

import org.treesitter.TSNode;

import java.util.HashMap;

public class IfLet2Match extends Mutator {
    @Override
    public HashMap<int[], String> searchCandidates(TSNode node, String srcCode) {
        HashMap<int[], String> edits = new HashMap<>();

        if (node.getType().equals("if_expression")) {
            TSNode condition = node.getChildByFieldName("condition");
            if (!condition.isNull() && condition.getType().equals("let_condition")) {
                TSNode pattern = condition.getChildByFieldName("pattern");
                TSNode value = condition.getChildByFieldName("value");
                TSNode consequence = node.getChildByFieldName("consequence");
                TSNode alternative = node.getChildByFieldName("alternative");
                if (!pattern.isNull() && !value.isNull() && !consequence.isNull()) {
                    TSNode type = pattern.getChildByFieldName("type");

                    if (!type.isNull() && srcCode.substring(type.getStartByte(), type.getEndByte()).equals("Some")) {
                        String patternCode = srcCode.substring(pattern.getStartByte(), pattern.getEndByte());
                        String valueCode = srcCode.substring(value.getStartByte(), value.getEndByte());
                        String consequenceCode = srcCode.substring(consequence.getStartByte(), consequence.getEndByte());

                        StringBuilder matchExpr = new StringBuilder();
                        matchExpr.append("match ").append(valueCode).append(" {\n");
                        matchExpr.append("    ").append(patternCode).append(" => ").append(consequenceCode).append(",\n");

                        if (!alternative.isNull()) {
                            TSNode alternative_block = alternative.getNamedChild(0);
                            if (!alternative_block.isNull() && alternative_block.getType().equals("block")) {
                                String alternative_blockCode = srcCode.substring(alternative_block.getStartByte(), alternative_block.getEndByte());
                                matchExpr.append("    _ => ").append(alternative_blockCode).append(",\n");
                            }
                        } else {
                            matchExpr.append("    _ => {},\n");
                        }
                        matchExpr.append("}");
                        int[] location = new int[]{node.getStartByte(), node.getEndByte()};
                        edits.put(location, matchExpr.toString());
                    }
                }
            }
        }

        for (int i = 0; i < node.getChildCount(); i++) {
            edits.putAll(searchCandidates(node.getChild(i), srcCode));
        }

        return edits;
    }
}
