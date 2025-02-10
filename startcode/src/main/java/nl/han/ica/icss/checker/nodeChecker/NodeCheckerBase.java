package nl.han.ica.icss.checker.nodeChecker;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.checker.SymbolTable;

public abstract class NodeCheckerBase{

    private final boolean shouldPushScope = false;
    protected ASTNode node;
    protected SymbolTable symbolTable;

    protected NodeCheckerBase(ASTNode node, SymbolTable symbolTable){
        this.node = node;
        this.symbolTable = symbolTable;
    }

    public boolean isShouldPushScope() {
        return shouldPushScope;
    }

    public ASTNode checkNode(){
        return node;
    }
}
