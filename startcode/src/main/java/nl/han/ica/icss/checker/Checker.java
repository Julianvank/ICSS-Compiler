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

//    private void walkAST(ASTNode node){
//
//
//        ArrayList<ASTNode> children = node.getChildren();
//
//        for (ASTNode cNode : children) {
//            walkAST(cNode);
//        }
//
//        NodeCheckerBase nodeChecker = nodeCheckerFactory.createNodeChecker(this, node);
//
//        if(nodeChecker != null) {
//            if (nodeChecker.isShouldPushScope()){
//                symbolTable.pushScope();
//            }
//        }
//
//        if (nodeChecker != null) {
//            node = nodeChecker.checkNode();
//        }
//
//
//        if (nodeChecker != null && nodeChecker.isShouldPushScope()) {
//            symbolTable.popScope();
//        }
//    }

//    private void traverseASTNode(ASTNode node) {
//        pushScope();
//
//        List<ASTNode> variables = new ArrayList<>();
//        List<ASTNode> declarations = new ArrayList<>();
//        List<ASTNode> ifStatements = new ArrayList<>();
//        List<ASTNode> stylerules = new ArrayList<>();
//
//        for (ASTNode n : node.getChildren()) {
//            if (n instanceof VariableAssignment) {
//                variables.add(n);
//            }
//            if (n instanceof Stylerule) {
//                stylerules.add(n);
////                traverseASTNode(n);
//            }
//            if (n instanceof Declaration) {
//                declarations.add(n);
//            }
//            if(n instanceof IfClause){
//                ifStatements.add(n);
//            }
//
//        }
//        variables.forEach(this::checkAndAssignVariable);
//        stylerules.forEach(this::traverseASTNode);
//        declarations.forEach((n) -> evaluateExpression(((Declaration) n).expression));
//        ifStatements.forEach(this::evaluateIfClause);
//
//        popScope();
//    }


//    private void evaluateElseClause(ASTNode astNode){
//        traverseASTNode(astNode);
//    }

//    private void evaluateIfClause(ASTNode astNode){
//        IfClause cNode = (IfClause) astNode;
//
//        if(!(getType(cNode.conditionalExpression) == ExpressionType.BOOL)){
//            astNode.setError("An if statement expects a conditional Boolean as expression");
//        }
//
//        traverseASTNode(astNode);
//
//        if(cNode.elseClause != null){
//            evaluateElseClause(astNode);
//        }
//    }

//    private void evaluateExpression(ASTNode node) {
//        if (node instanceof VariableReference) {
//            if (loopUpReference(node) == ExpressionType.UNDEFINED) {
//                String error = "Variable " + node + " is not declared.";
//                node.setError(error);
//            }
//        } else if (node instanceof Expression) {
//            if (getType(node) == ExpressionType.UNDEFINED) {
//                node.setError("Invalid expression use");
//            }
//        }
//    }

//    private ExpressionType loopUpReference(ASTNode node) {
//        LinkedListIterator<HashMap<String, ExpressionType>> itr = symbolTable.zeroth();
//
//        String name = ((VariableReference) node).name;
//        for (; itr.isValid(); itr.advance()) {
//            if (itr.retrieve() != null && itr.retrieve().containsKey(name)) {
//                return itr.retrieve().get(name);
//            }
//        }
//        return ExpressionType.UNDEFINED;
//    }


//    private void checkAndAssignVariable(ASTNode node) {
//
//        String name = ((VariableAssignment) node).name.name;
//        ExpressionType type = getType(((VariableAssignment) node).expression);
//
//        symbolTable.getFirst().put(name, type);
//    }

    /**
     * Recursivly find type of expression. Along the way searches for errors and marks these accordingly.
     *
     * @param node the node of the expression to get the type of.
     * @return the Expression type
     */

    //TODO replace expressionType met PropertyName in de literals
//    private ExpressionType getType(ASTNode node) {
//        if (node instanceof Literal) {
//            return ((Literal) node).getType();
//        }
//
//        if (node instanceof VariableReference) {
//            return loopUpReference(node);
//        }
//
//
//        ExpressionType lhsType = getType(((Operation) node).lhs);
//        ExpressionType rhsType = getType(((Operation) node).rhs);
//
//        if (lhsType.getInSymbol() == 0 || rhsType.getInSymbol() == 0) {
//            node.setError("Expected operable laterals to be used.");
//            return ExpressionType.UNDEFINED;
//        }
//
//        if (node instanceof MultiplyOperation) {
//            if ((lhsType != ExpressionType.SCALAR && rhsType != ExpressionType.SCALAR)) {
//                node.setError("Multiplication expression expects use an instance of Scalar");
//                return ExpressionType.UNDEFINED;
//            }
//
//            if (lhsType.getTopSymbol() <= rhsType.getInSymbol()) {
//                return rhsType;
//            } else {
//                return lhsType;
//            }
//        }
//
//        if (node instanceof AddOperation || node instanceof SubtractOperation) {
//            if (!(lhsType == rhsType)) {
//                node.setError("Add or Subtract expressions expect use of equal typing");
//                return ExpressionType.UNDEFINED;
//            }
//            return rhsType;
//        }
//
//        return ExpressionType.UNDEFINED;
//    }
//    }

}
