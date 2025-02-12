package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.*;
import nl.han.ica.datastructures.LinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.nodeChecker.ExpressionChecker;
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

        symbolTable.pushScope();
        walkAST(ast.root);
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
            nodeChecker.checkNode();
        }

        //IF CHILDREN
        if (!children.isEmpty()) {
            ArrayList<ASTNode> newChildren = new ArrayList<>();
            //FOREACH CHILD C
            for (ASTNode cNode : children) {
                ASTNode walkedNode = walkAST(cNode);
//                node.removeChild(cNode);
//                node.addChild(walkedNode);
            }

        }

        //CHECK SCOPE
        modifyScope(node, POP_SCOPE);

        return node;
    }

    private void modifyScope(ASTNode node, boolean pushOrPop) {
        NodeCheckerBase nodeChecker = nodeCheckerFactory.createNodeChecker(this, node);

        if (nodeChecker == null) return;
        if (!nodeChecker.isShouldPushScope()) return;

        if(pushOrPop == PUSH_SCOPE){
            symbolTable.pushScope();
        } else if (pushOrPop == POP_SCOPE) {
            symbolTable.popScope();
        }
    }

}
