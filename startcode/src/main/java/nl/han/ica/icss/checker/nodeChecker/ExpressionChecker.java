package nl.han.ica.icss.checker.nodeChecker;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.datastructures.SymbolTable;
import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.VariableReference;

public class ExpressionChecker extends NodeCheckerBase {

    VariableReferenceChecker referenceChecker;
    OperationChecker operationChecker;
    public ExpressionChecker(SymbolTable table) {
        super(table);
    }

    @Override
    public Expression checkNode(ASTNode node) {
        Expression expression = (Expression) node;

        if(expression instanceof VariableReference){
            referenceChecker = new VariableReferenceChecker(symbolTable);
            expression = (Expression) referenceChecker.checkNode(node);
        }

        if(expression instanceof Operation){
            operationChecker = new OperationChecker(symbolTable);
            expression = (Expression) operationChecker.checkNode(node);
        }

        return expression;
    }
}
