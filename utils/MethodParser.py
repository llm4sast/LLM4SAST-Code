from tree_sitter import Node

def GetBlockNode(node: Node) -> Node | None:
    if node.type == "function_item":
        for child in node.children:
            if child.type == "block":
                return child
    return None

def GetReturnNode(node: Node) -> Node | None:
    if node.type == "function_item":
        for child in node.children:
            if child.type == "return_type":
                return child
    return None