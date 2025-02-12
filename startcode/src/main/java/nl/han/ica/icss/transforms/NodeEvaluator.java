package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.checker.SymbolTable;

public abstract class NodeEvaluator {
    ASTNode node;
    SymbolTable table;

    NodeEvaluator(ASTNode node, SymbolTable table){
        this.node = node;
        this.table = table;
    }
    protected abstract ASTNode evaluateNode();
}
