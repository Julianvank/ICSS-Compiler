package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.*;
import nl.han.ica.datastructures.LinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.*;


public class Checker {

    //    private HANStack<HashMap<String, ExpressionType>> symbolTable;
    private LinkedList<HashMap<String, ExpressionType>> symbolTable;

    public void check(AST ast) {
        symbolTable = new LinkedList<>();
        pushScope();
        traverseAST(ast.root);
    }

    public void pushScope() {
        symbolTable.addFirst(new HashMap<>());
    }

    public void popScope() {
        symbolTable.removeFirst();
    }

    private void traverseAST(ASTNode node) {

//        for (ASTVisitor vis = new ASTVisitor(node); vis.isValid(); vis.advance()) {
        for (ASTNode cNode : node.getChildren()) {
            if (cNode instanceof VariableAssignment) {
                checkAndAssignVariable(cNode);
            }
            if (cNode instanceof Stylerule) {
                checkStyleRule(cNode);
            }
        }
    }

    private void checkStyleRule(ASTNode node) {
        pushScope();

        List<ASTNode> variables = new ArrayList<>();
        List<ASTNode> declarations = new ArrayList<>();
        List<ASTNode> ifStatements = new ArrayList<>();

        for (ASTNode n : node.getChildren()) {
            if (n instanceof VariableAssignment) {
                variables.add(n);
            }
            if (n instanceof Declaration) {
                declarations.add(n);
            }
            if(n instanceof IfClause){
                ifStatements.add(n);
            }

        }
        variables.forEach((this::checkAndAssignVariable));
        declarations.forEach((n) -> evaluateExpression(((Declaration) n).expression));
        ifStatements.forEach(this::evaluateIfClause);

        popScope();
    }

    private void evaluateElseClause(ASTNode astNode){
        checkStyleRule(astNode);
    }

    private void evaluateIfClause(ASTNode astNode){
        IfClause cNode = (IfClause) astNode;

        if(!(getType(cNode.conditionalExpression) == ExpressionType.BOOL)){
            astNode.setError("An if statement expects a conditional Boolean as expression");
        }

        checkStyleRule(astNode);
    }

    private void evaluateExpression(ASTNode node) {
        if (node instanceof VariableReference) {
            if (loopUpReference(node) == ExpressionType.UNDEFINED) {
                String error = "Variable " + node + " is not declared.";
                node.setError(error);
            }
        } else if (node instanceof Expression) {
            if (getType(node) == ExpressionType.UNDEFINED) {
                node.setError("Invalid expression use");
            }
        }
    }

    private ExpressionType loopUpReference(ASTNode node) {
        LinkedListIterator<HashMap<String, ExpressionType>> itr = symbolTable.zeroth();

        String name = ((VariableReference) node).name;
        for (; itr.isValid(); itr.advance()) {
            if (itr.retrieve() != null && itr.retrieve().containsKey(name)) {
                return itr.retrieve().get(name);
            }
        }
        return ExpressionType.UNDEFINED;
    }


    private void checkAndAssignVariable(ASTNode node) {

        String name = ((VariableAssignment) node).name.name;
        ExpressionType type = getType(((VariableAssignment) node).expression);

        symbolTable.getFirst().put(name, type);
    }

    /**
     * Recursivly find type of expression. Along the way searches for errors and marks these accordingly.
     *
     * @param node the node of the expression to get the type of.
     * @return the Expression type
     */
    private ExpressionType getType(ASTNode node) {
        if (node instanceof Literal) {
            return ((Literal) node).getType();
        }

        if (node instanceof VariableReference) {
            return loopUpReference(node);
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
