package org.trust.mutators;

import org.treesitter.TSNode;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Match2UnwrapOrElse extends Mutator {
    @Override
    public HashMap<int[], String> searchCandidates(TSNode node, String srcCode) {
        HashMap<int[], String> edits = new HashMap<>();

        if (node.getType().equals("match_expression")) {
            TSNode valueNode = node.getChildByFieldName("value");
            TSNode bodyNode = node.getChildByFieldName("body");
            String valueType = getExpressionType(bodyNode, srcCode);
            List<TSNode> matchArms = IntStream.range(0, bodyNode.getNamedChildCount())
                    .mapToObj(bodyNode::getNamedChild)
                    .filter(child -> !child.isNull() && child.getType().equals("match_arm"))
                    .collect(Collectors.toList());
            if (matchArms.size() != 2) {
                return edits;
            }

            if (valueType.equals("Option") || valueType.equals("Result")) {
                // 获取第一个arm
                TSNode firstArm = matchArms.get(0);
                TSNode firstPattern = firstArm.getChildByFieldName("pattern");
                TSNode firstBody = firstArm.getChildByFieldName("value");

                // 获取第二个arm
                TSNode secondArm = matchArms.get(1);
                TSNode secondPattern = secondArm.getChildByFieldName("pattern");
                TSNode secondBody = secondArm.getChildByFieldName("value");


                if (!firstPattern.isNull() && !firstBody.isNull() &&
                        !secondPattern.isNull() && !secondBody.isNull()) {

                    String firstPatternCode = srcCode.substring(firstPattern.getStartByte(), firstPattern.getEndByte());
                    String secondPatternCode = srcCode.substring(secondPattern.getStartByte(), secondPattern.getEndByte());

                    String firstBodyCode = srcCode.substring(firstBody.getStartByte(), firstBody.getEndByte());
                    String secondBodyCode = srcCode.substring(secondBody.getStartByte(), secondBody.getEndByte());


                    // 检查哪个arm匹配Some/Ok
                    if (firstPatternCode.startsWith("Some") || firstPatternCode.startsWith("Ok")) {
                        // 第一个arm匹配Some/Ok
                        String valueCode = srcCode.substring(valueNode.getStartByte(), valueNode.getEndByte());
                        String unwrapExpr = valueCode + ".unwrap_or_else(|| " + secondBodyCode + ")";
                        // 如果第一个arm有额外操作，需要保留
                        if (firstBodyCode.equals(firstPatternCode.replaceFirst("^Some\\(", "").replaceFirst("\\)$", ""))) {
                            int[] location = new int[]{node.getStartByte(), node.getEndByte()};
                            edits.put(location, unwrapExpr);
                        }


                    } else if (secondPatternCode.startsWith("Some") || secondPatternCode.startsWith("Ok")) {
                        // 第二个arm匹配Some/Ok
                        String valueCode = srcCode.substring(valueNode.getStartByte(), valueNode.getEndByte());
                        String unwrapExpr = valueCode + ".unwrap_or_else(|| " + firstBodyCode + ")";

                        // 如果第二个arm有额外操作，需要保留
                        if (secondBodyCode.equals(secondPatternCode.replaceFirst("^Some\\(", "").replaceFirst("\\)$", ""))) {
                            int[] location = new int[]{node.getStartByte(), node.getEndByte()};
                            edits.put(location, unwrapExpr);
                        }


                    }
                }

            }

        }

        for (int i = 0; i < node.getChildCount(); i++) {
            edits.putAll(searchCandidates(node.getChild(i), srcCode));
        }

        return edits;
    }

    private String getExpressionType(TSNode node, String srcCode) {
        // 简单判断表达式类型，实际实现需要更复杂的类型推断
        String expr = srcCode.substring(node.getStartByte(), node.getEndByte());
        if (expr.contains("Some") || expr.contains("None")) {
            return "Option";
        } else if (expr.contains("Ok") || expr.contains("Err")) {
            return "Result";
        }
        return "Unknown";
    }
}
