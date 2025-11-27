package org.trust.mutators;

import org.treesitter.*;


import java.util.*;

public class ReferenceAdder extends Mutator {
    @Override
    public HashMap<int[], String> searchCandidates(TSNode node, String srcCode) {
        HashMap<int[], String> edits = new HashMap<>();

        if (node.isNull()) {
            return edits;
        }

        // 检查函数是否在impl块中
        if (node.getType().equals("impl_item")) {
            return edits;
        }

        if (node.getType().equals("function_item")) {
            // 检查函数是否有const修饰符
            boolean isConst = isConstFunction(node, srcCode);

            TSNode type_parameters = node.getChildByFieldName("type_parameters");
            // 检查函数是否有泛型参数
            if (!type_parameters.isNull()) {
                // 如果有泛型参数，跳过这个函数
                return edits;
            }

            TSNode parameters = node.getChildByFieldName("parameters");
            TSNode body = node.getChildByFieldName("body");
            
            if (!parameters.isNull() && !body.isNull()) {
                for (int i = 0; i < parameters.getNamedChildCount(); i++) {
                    TSNode param = parameters.getNamedChild(i);
                    if (param.getType().equals("parameter")) {
                        TSNode type = param.getChildByFieldName("type");
                        TSNode pattern = param.getChildByFieldName("pattern");
                        if (!type.isNull() && !type.getType().equals("reference_type") && !pattern.isNull()) {

                            boolean isMut = param.getNamedChild(0).getType().equals("mutable_specifier");

                            // mutable references不能在const函数中用
                            if (isConst && isMut) {
                                continue;
                            }

                            String paramCode = srcCode.substring(param.getStartByte(), param.getEndByte());
                            String typeCode = srcCode.substring(type.getStartByte(), type.getEndByte());
                            String paramName = srcCode.substring(pattern.getStartByte(), pattern.getEndByte());
                            // 构建新的代码
                            StringBuilder newCode = new StringBuilder(srcCode);

                            // 在方法体的block内部开始处添加解引用赋值语句
                            String derefAssignment = isMut ? 
                                "\n    let mut " + paramName + " = *" + paramName + ";" :
                                "\n    let " + paramName + " = *" + paramName + ";";
                            int insertPosition = body.getStartByte() + 1;
                            newCode.insert(insertPosition, derefAssignment);

                            // 添加引用
                            String newParamCode = isMut ?
                                paramCode.replace(typeCode, "&mut " + typeCode) :
                                paramCode.replace(typeCode, "&" + typeCode);
                            newCode.replace(param.getStartByte(), param.getEndByte(), newParamCode);

                            // 重新解析新代码
                            TSParser parser = new TSParser();
                            TSLanguage rust = new TreeSitterRust();
                            parser.setLanguage(rust);
                            TSTree newTree = parser.parseString(null, newCode.toString());
                            TSNode newRootNode = newTree.getRootNode();

                            // 修改所有调用该函数的地方
                            List<int[]> callSites = findFunctionCalls(newRootNode, newCode.substring(node.getChildByFieldName("name").getStartByte(), node.getChildByFieldName("name").getEndByte()), newCode.toString());
                            for (int j = callSites.size() - 1; j >= 0; j--) {
                                int[] callSite = callSites.get(j);
                                TSNode arguments = newRootNode.getNamedDescendantForByteRange(callSite[0], callSite[1]).getChildByFieldName("arguments");

                                if (!arguments.isNull()) {
                                    TSNode arg = arguments.getNamedChild(i); // 使用i来匹配对应的参数位置
                                    String argCode = newCode.substring(arg.getStartByte(), arg.getEndByte());
                                    newCode.replace(arg.getStartByte(), arg.getEndByte(), isMut ? "&mut (" + argCode + ")" : "&(" + argCode + ")");
                                }

                            }

                            int[] location = new int[]{0, srcCode.length()};
                            edits.put(location, newCode.toString());
                        }
                    }
                }
            }
        }

        for (int i = 0; i < node.getNamedChildCount(); i++) {
            edits.putAll(searchCandidates(node.getNamedChild(i), srcCode));
        }

        return edits;
    }


}
