package org.trust.mutators;

import org.treesitter.TSNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignmentAdder extends Mutator {
    @Override
    public HashMap<int[], String> searchCandidates(TSNode node, String srcCode) {
        HashMap<int[], String> edits = new HashMap<>();
        if (node.getType().equals("let_declaration")) {
            boolean isMutable = false;
            String variableName = "";

            for (int i = 0; i < node.getNamedChildCount(); i++) {
                TSNode child = node.getNamedChild(i);
                if (child.getType().equals("mutable_specifier")) {
                    isMutable = true;
                }
            }
            TSNode pattern = node.getChildByFieldName("pattern");
            if (!pattern.isNull()) {
                if (pattern.getType().equals("tuple_pattern")) {
                    // 处理元组模式
                    List<String> variableNames = new ArrayList<>();
                    for (int i = 0; i < pattern.getNamedChildCount(); i++) {
                        TSNode tupleElement = pattern.getNamedChild(i);
                        if (tupleElement.getType().equals("identifier")) {
                            String name = srcCode.substring(tupleElement.getStartByte(), tupleElement.getEndByte());
                            if (!name.equals("_")) {
                                variableNames.add(name);
                            }
                        }
                    }
                    
                    // 为每个变量生成新的赋值语句

                    for (String name : variableNames) {
                        String assignmentCode = srcCode.substring(node.getStartByte(), node.getEndByte());
                        StringBuilder newAssignmentCode = new StringBuilder(assignmentCode);
                        String newVariableName = "_new_" + name;
                        newAssignmentCode.append("\n    let ").append(isMutable ? "mut " : "").append(newVariableName).append(" = ").append(name).append(";");
                        // 搜索并替换所有相关标识符
                        TSNode nextNamedSibling = node.getNextNamedSibling();
                        List<int[]> identifiersLocations = new ArrayList<>();
                        while (!nextNamedSibling.isNull()) {
                            identifiersLocations.addAll(searchIdentifiers(nextNamedSibling, name, srcCode));

                            nextNamedSibling = nextNamedSibling.getNextNamedSibling();
                        }

                        StringBuilder replaceCode = new StringBuilder(srcCode);
                        for (int i = identifiersLocations.size() - 1; i >= 0; i--) {
                            int[] idLocation = identifiersLocations.get(i);
                            TSNode identifierNode = node.getParent().getDescendantForByteRange(idLocation[0], idLocation[1]);
                            String originalName = srcCode.substring(idLocation[0], idLocation[1]);
                            String newName = "_new_" + originalName;
                            if (identifierNode.getParent().getType().equals("shorthand_field_initializer") && replaceCode.substring(idLocation[0], idLocation[1]).equals(originalName)) {
                                replaceCode.replace(idLocation[0], idLocation[1], originalName + ":" + newName);
                            } else {
                                replaceCode.replace(idLocation[0], idLocation[1], newName);

                            }
                        }
                        replaceCode.replace(node.getStartByte(), node.getEndByte(), newAssignmentCode.toString());
                        int[] location = new int[]{0, srcCode.length()};
                        edits.put(location, replaceCode.toString());
                    }

                    return edits;
                } else {
                    variableName = srcCode.substring(pattern.getStartByte(), pattern.getEndByte());
                }
            }

            // 如果变量名为_，则跳过
            if (variableName.equals("_")) {
                return edits;
            }

            String assignmentCode = srcCode.substring(node.getStartByte(), node.getEndByte());
            String newVariableName = "_new_" + variableName;
            String newAssignmentCode = assignmentCode + "\n    let " + (isMutable ? "mut " : "") + newVariableName + " = " + variableName + ";";

            // 在当前作用域中搜索所有identifier节点
            TSNode nextNamedSibling = node.getNextNamedSibling();
            List<int[]> identifiersLocations = new ArrayList<>();
            while (!nextNamedSibling.isNull()) {
                identifiersLocations.addAll(searchIdentifiers(nextNamedSibling, variableName, srcCode));
                nextNamedSibling = nextNamedSibling.getNextNamedSibling();
            }

            // 使用StringBuilder从后往前替换变量名，避免替换后索引位置变化的影响
            StringBuilder replaceCode = new StringBuilder(srcCode);
            for (int i = identifiersLocations.size() - 1; i >= 0; i--) {
                int[] idLocation = identifiersLocations.get(i);
                TSNode identifierNode = node.getParent().getDescendantForByteRange(idLocation[0], idLocation[1]);
                // 处理用变量来初始化struct的connor case
                if (identifierNode.getParent().getType().equals("shorthand_field_initializer") && replaceCode.substring(idLocation[0], idLocation[1]).equals(variableName)) {
                    replaceCode.replace(idLocation[0], idLocation[1], variableName + ":" + newVariableName);
                } else {
                    replaceCode.replace(idLocation[0], idLocation[1], newVariableName);
                }
            }

            // 替换let_declaration的位置
            replaceCode.replace(node.getStartByte(), node.getEndByte(), newAssignmentCode);

            int[] location = new int[]{0, srcCode.length()};
            edits.put(location, replaceCode.toString());
        }
        for (int i = 0; i < node.getNamedChildCount(); i++) {
            edits.putAll(searchCandidates(node.getNamedChild(i), srcCode));
        }
        return edits;
    }

    private List<int[]> searchIdentifiers(TSNode node, String variableName, String srcCode) {
        List<int[]> locations = new ArrayList<>();
        if (node.isNull() || node.getType().equals("scoped_identifier")) {
            return locations;
        }
        if (node.getType().equals("let_declaration")) {
            TSNode pattern = node.getChildByFieldName("pattern");
            if (!pattern.isNull()) {
                String patternName = srcCode.substring(pattern.getStartByte(), pattern.getEndByte());
                if (patternName.equals(variableName)) {
                    TSNode value = node.getChildByFieldName("value");
                    if (!value.isNull()) {
                        locations.addAll(searchIdentifiers(value, variableName, srcCode));
                    }
                    return locations;
                }
            }
        }

        if (node.getType().equals("identifier")) {
            String identifierName = srcCode.substring(node.getStartByte(), node.getEndByte());
            if (identifierName.equals(variableName)) {
                int[] location = new int[]{node.getStartByte(), node.getEndByte()};
                locations.add(location);
            }
        }

        for (int i = 0; i < node.getNamedChildCount(); i++) {
            locations.addAll(searchIdentifiers(node.getNamedChild(i), variableName, srcCode));
        }

        return locations;
    }

}