package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.*;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.checker.nodeChecker.NodeCheckerBase;

import java.util.*;


public class Checker {

    private SymbolTable symbolTable;
    private NodeCheckerFactory nodeCheckerFactory;
    private static final boolean PUSH_SCOPE = true;
    private static final boolean POP_SCOPE = false;


    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void check(AST ast) {
        symbolTable = new SymbolTable();
        nodeCheckerFactory = new NodeCheckerFactory();

        symbolTable.pushScope(ast.root);
        walkAST(ast.root);
        ast.setSymbolTable(symbolTable);
    }


    private void walkAST(ASTNode node) {
        modifyScope(node, PUSH_SCOPE);

        ArrayList<ASTNode> children = new ArrayList<>(node.getChildren());

        NodeCheckerBase nodeChecker;
        nodeChecker = nodeCheckerFactory.createNodeChecker(this, node);

        if(nodeChecker != null) {
            nodeChecker.checkNode(node);
        }

        if (!children.isEmpty()) {
            for (ASTNode cNode : children) {
                walkAST(cNode);
            }
        }

        modifyScope(node, POP_SCOPE);

    }

    private void modifyScope(ASTNode node, boolean pushOrPop) {
        if (NodeCheckerFactory.shouldScopeBePushed(node)) return;

        if(pushOrPop == PUSH_SCOPE){
            symbolTable.pushScope(node);
        } else if (pushOrPop == POP_SCOPE) {
            symbolTable.popScope();
        }
    }

}
