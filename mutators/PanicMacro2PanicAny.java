package org.trust.mutators;

import org.treesitter.TSNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanicMacro2PanicAny extends Mutator {
    @Override
    public HashMap<int[], String> searchCandidates(TSNode node, String srcCode) {
        HashMap<int[], String> edits = new HashMap<>();
        if (node.getType().equals("macro_invocation")) {
            TSNode macro_identifier = node.getChildByFieldName("macro");
            String macroCode = srcCode.substring(macro_identifier.getStartByte(), macro_identifier.getEndByte());
            if (macroCode.equals("panic")) {
                TSNode panic_string_literal = node.getNamedChild(1);
                if (panic_string_literal.getNamedChildCount() == 0) {
                    int[] location = new int[]{node.getStartByte(), node.getEndByte()};
                    String replaceCode = srcCode.substring(node.getStartByte(), node.getEndByte()).replace("panic!()", "std::panic::panic_any(())");
                    edits.put(location, replaceCode);
                } else {
                    int[] location = new int[]{node.getStartByte(), node.getEndByte()};
                    String replaceCode = srcCode.substring(node.getStartByte(), node.getEndByte()).replace("panic!", "std::panic::panic_any");
                    edits.put(location, replaceCode);
                }
            } else if (macroCode.equals("std::panic")) {
                TSNode panic_string_literal = node.getNamedChild(1);
                if (panic_string_literal.getNamedChildCount() == 0) {
                    int[] location = new int[]{node.getStartByte(), node.getEndByte()};
                    String replaceCode = srcCode.substring(node.getStartByte(), node.getEndByte()).replace("std::panic!()", "std::panic::panic_any(())");
                    edits.put(location, replaceCode);
                } else {
                    int[] location = new int[]{node.getStartByte(), node.getEndByte()};
                    String replaceCode = srcCode.substring(node.getStartByte(), node.getEndByte()).replace("std::panic!", "std::panic::panic_any");
                    edits.put(location, replaceCode);
                }
            }
        }
        for (int i = 0; i < node.getNamedChildCount(); i++) {
            edits.putAll(searchCandidates(node.getNamedChild(i), srcCode));
        }
        return edits;
    }


}