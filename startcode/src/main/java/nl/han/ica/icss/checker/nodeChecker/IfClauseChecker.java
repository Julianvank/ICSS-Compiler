package nl.han.ica.icss.checker.nodeChecker;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.IfClause;
import nl.han.ica.icss.ast.VariableReference;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.datastructures.SymbolTable;

public class IfClauseChecker extends NodeCheckerBase {

    ExpressionChecker expressionChecker;

    public IfClauseChecker(SymbolTable table) {
        super(table);
    }

    public ASTNode checkNode(ASTNode node) {
        IfClause newNode = (IfClause) node;

        expressionChecker = new ExpressionChecker(symbolTable);
        newNode.conditionalExpression = expressionChecker.checkNode(newNode.conditionalExpression);

        if (newNode.conditionalExpression instanceof VariableReference)
            newNode.conditionalExpression = symbolTable.findSymbol(((VariableReference) newNode.conditionalExpression).name);


        if ((newNode.conditionalExpression instanceof BoolLiteral))
            return node;


        node.setError("An ifClause expects a BoolLiteral as expression");
        return node;
    }

}
