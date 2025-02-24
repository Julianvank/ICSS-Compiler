package nl.han.ica.icss.checker.nodeChecker;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.datastructures.SymbolTable;

public abstract class NodeCheckerBase{

    protected SymbolTable symbolTable;

    protected NodeCheckerBase(SymbolTable symbolTable){
        this.symbolTable = symbolTable;
    }

    public ASTNode checkNode(ASTNode node){
        return node;
    }
}
