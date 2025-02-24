package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.*;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.checker.nodeChecker.NodeCheckerBase;

import java.util.*;


public class Checker {

    private SymbolTable symbolTable;
    private AST ast;
    private NodeCheckerFactory nodeCheckerFactory;
    private static final boolean PUSH_SCOPE = true;
    private static final boolean POP_SCOPE = false;


    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void check(AST ast) {
        this.ast = ast;
        symbolTable = new SymbolTable();
        nodeCheckerFactory = new NodeCheckerFactory();

        symbolTable.pushScope(ast.root);
        walkAST(ast.root);
        ast.setSymbolTable(symbolTable);
    }


    private ASTNode walkAST(ASTNode node) {
        modifyScope(node, PUSH_SCOPE);

        //GET CHILDREN
        ArrayList<ASTNode> children = new ArrayList<>(node.getChildren());

        //CREATE FACTORY
        NodeCheckerBase nodeChecker;
        nodeChecker = nodeCheckerFactory.createNodeChecker(this, node);

        //CHECK NODE
        if(nodeChecker != null) {
            nodeChecker.checkNode(node);
        }

        //IF CHILDREN
        if (!children.isEmpty()) {
            ArrayList<ASTNode> newChildren = new ArrayList<>();
            //FOREACH CHILD C
            for (ASTNode cNode : children) {
                ASTNode walkedNode = walkAST(cNode);

            }

        }
        //CHECK SCOPE
        modifyScope(node, POP_SCOPE);

        return node;
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
