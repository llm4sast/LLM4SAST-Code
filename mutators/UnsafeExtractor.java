package org.trust.mutators;

import org.apache.commons.lang3.ObjectUtils;
import org.treesitter.TSNode;
import org.treesitter.TSParser;
import org.treesitter.TSTree;
import org.treesitter.TreeSitterRust;

import java.util.HashMap;

public class UnsafeExtractor extends Mutator {
    @Override
    public HashMap<int[], String> searchCandidates(TSNode node, String srcCode) {
        HashMap<int[], String> edits = new HashMap<>();

        // 查找struct定义
        if (node.getType().equals("struct_item")) {
            // 查找对应的impl块
            TSNode implNode = findImplForStruct(node, srcCode);
            if (!implNode.isNull()) {
                // 在impl块中查找Drop实现
                TSNode dropImpl = findDropImpl(implNode, srcCode);

                if (!dropImpl.isNull()) {
                    // 获取整个drop方法体
                    TSNode dropBody = dropImpl.getChildByFieldName("body");
                    if (!dropBody.isNull()) {
                        // 检查方法体中是否有unsafe块
                        TSNode unsafeBlock = findUnsafeBlockInDrop(dropImpl, srcCode);
                        if (!unsafeBlock.isNull()) {
                            // 提取整个方法体内容
                            String dropCode = srcCode.substring(dropBody.getStartByte(), dropBody.getEndByte());

                            // 生成新的方法名
                            String methodName = "unsafe_drop_impl";

                            // 构建新的方法
                            String newMethod = "\n    fn " + methodName + "(&mut self) " + dropCode;

                            // 替换drop方法体为方法调用
                            String methodCall = "self." + methodName + "();";

                            // 构建新的impl块
                            String structName = srcCode.substring(
                                    node.getChildByFieldName("name").getStartByte(),
                                    node.getChildByFieldName("name").getEndByte()
                            );
                            String newImplBlock = "\n\nimpl " + structName + " {" + newMethod + "\n}";

                            // 构建新的源代码
                            String newSrcCode = srcCode.substring(0, dropBody.getStartByte())
                                    + "{" + methodCall + "}"
                                    + srcCode.substring(dropBody.getEndByte())
                                    + newImplBlock;

                            // 替换整个源代码
                            edits.put(new int[]{0, srcCode.length()}, newSrcCode);
                        }
                    }
                }
            }
        }

        // 递归处理子节点
        for (int i = 0; i < node.getNamedChildCount(); i++) {
            edits.putAll(searchCandidates(node.getNamedChild(i), srcCode));
        }

        return edits;
    }

    private TSNode findImplForStruct(TSNode structNode, String srcCode) {
        String structName = srcCode.substring(
                structNode.getChildByFieldName("name").getStartByte(),
                structNode.getChildByFieldName("name").getEndByte()
        );
        // 在文件中查找对应的impl块
        TSParser parser = new TSParser();
        parser.setLanguage(new TreeSitterRust());
        TSTree tree = parser.parseString(null, srcCode);
        TSNode root = tree.getRootNode();
        for (int i = 0; i < root.getNamedChildCount(); i++) {
            TSNode child = root.getNamedChild(i);
            if (child.getType().equals("impl_item")) {
                TSNode implType = child.getChildByFieldName("type");
                if (!implType.isNull()) {
                    String implTypeName = srcCode.substring(implType.getStartByte(), implType.getEndByte());
                    if (implTypeName.equals(structName)) {
                        return child;
                    }
                }
            }
        }
        return new TSNode();
    }

    private TSNode findDropImpl(TSNode implNode, String srcCode) {
        TSNode implBodyNode = implNode.getChildByFieldName("body");
        if (!implBodyNode.isNull()) {
            for (int i = 0; i < implBodyNode.getNamedChildCount(); i++) {
                TSNode child = implBodyNode.getNamedChild(i);
                if (child.getType().equals("function_item")) {
                    TSNode nameNode = child.getChildByFieldName("name");
                    if (!nameNode.isNull()) {
                        String fnName = srcCode.substring(nameNode.getStartByte(), nameNode.getEndByte());
                        if (fnName.equals("drop")) {
                            return child;
                        }
                    }
                }
            }
        }
        return new TSNode();
    }

    private TSNode findUnsafeBlockInDrop(TSNode dropNode, String srcCode) {
        TSNode body = dropNode.getChildByFieldName("body");
        if (!body.isNull()) {
            return findUnsafeBlockRecursive(body, srcCode);
        }
        return new TSNode();
    }

    private TSNode findUnsafeBlockRecursive(TSNode node, String srcCode) {
        // 检查当前节点是否是unsafe_block
        if (node.getType().equals("unsafe_block")) {
            return node;
        }

        // 递归检查所有子节点
        for (int i = 0; i < node.getNamedChildCount(); i++) {
            TSNode child = node.getNamedChild(i);
            TSNode result = findUnsafeBlockRecursive(child, srcCode);
            if (!result.isNull()) {
                return result;
            }
        }

        return new TSNode();
    }
}