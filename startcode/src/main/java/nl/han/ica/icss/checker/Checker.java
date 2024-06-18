package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.LinkedList;
import nl.han.ica.datastructures.LinkedListIterator;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;


public class Checker {

    //Gebruik de LinkedList om de scope van variabelen bij te houden.
    private LinkedList<HashMap<String, ExpressionType>> variableTypes;
//    private IHANLinkedList<ASTNode> astList;

    /*
     * Een ASTVisitor die een check functie heeft. De check functiemaakt gebruik van een bepaalde checker.
     * Dus variable assignment worden gechecked met een instance van INodeChecker
     *
     */
    //TODO mark every semantic error with the setError method.
    public void check(AST ast) {
        variableTypes = new LinkedList<>();
        variableTypes.addFirst(new HashMap<String, ExpressionType>());
        traverseAST(ast.root);
    }

    public void pushScope() {
        variableTypes.addFirst(new HashMap<>());
    }

    private void traverseAST(ASTNode node) {
        int depth = 0;

        for (ASTVisitor vis = new ASTVisitor(node); vis.isValid(); vis.advance()) {
            if (vis.retrieve() instanceof VariableAssignment) {
                checkAndAssignVariable((VariableAssignment) vis.retrieve());
            }
            if (vis.retrieve() instanceof Stylerule) {
                checkStyleRule((Stylerule) vis.retrieve());
            }

        }
//        printVariables();
    }

    private void checkStyleRule(Stylerule node) {
        pushScope();
        for (ASTVisitor vis = new ASTVisitor(node); vis.isValid(); vis.advance()) {
            if (vis.retrieve() instanceof VariableAssignment) {
                checkAndAssignVariable((VariableAssignment) vis.retrieve());
            }
        }
    }

    private void checkAndAssignVariable(VariableAssignment current) {
        String name = current.name.name;
        ExpressionType type = getType(current.expression);

        variableTypes.getFirst().put(name, type);
        System.out.println(name + " " + type);

    }

    /**
     * Recursivly find type of expression. Along the way searches for errors and marks these accordingly.
     *
     * @param node the node of the expression to get the type of.
     * @return the Expression type
     */
    private ExpressionType getType(ASTNode node) {
        if (node.getChildren().isEmpty()) {
            return ((Literal) node).getType();
        }

        ExpressionType lhsType = getType(((Operation) node).lhs);
        ExpressionType rhsType = getType(((Operation) node).rhs);

        if (lhsType.getInSymbol() == 0 || rhsType.getInSymbol() == 0) {
            node.setError("Expected operable laterals to be used.");
            return ExpressionType.UNDEFINED;
        }

        if (node instanceof MultiplyOperation) {
            if ((lhsType != ExpressionType.SCALAR && rhsType != ExpressionType.SCALAR)) {
                node.setError("Multiplication expression expects use an instance of Scalar");
                return ExpressionType.UNDEFINED;
            }

            if (lhsType.getTopSymbol() <= rhsType.getInSymbol()) {
                return rhsType;
            } else {
                return lhsType;
            }
        }

        if (node instanceof AddOperation || node instanceof SubtractOperation) {
            if (!(lhsType == rhsType)) {
                node.setError("Add or Subtract expressions expect use of equal typing");
                return ExpressionType.UNDEFINED;
            }
            return rhsType;
        }

        return ExpressionType.UNDEFINED;
    }

}
