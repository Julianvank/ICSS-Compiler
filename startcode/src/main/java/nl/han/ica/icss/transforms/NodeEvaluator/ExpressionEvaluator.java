package nl.han.ica.icss.transforms.NodeEvaluator;

import nl.han.ica.datastructures.SymbolTable;
import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.VariableReference;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;

public class ExpressionEvaluator extends NodeEvaluatorBase {
    public ExpressionEvaluator(SymbolTable table) {
        super(table);
    }

    @Override
    public ASTNode evaluate(ASTNode node) {
        super.evaluate(node);
        //IF node instance of variableReference
        // node = Get expression;

        //IF node instance of operation
        //node = Collapse operation;

        if (node instanceof VariableReference) {
            node = table.findSymbol(((VariableReference) node).name);
        }

        if (node instanceof Operation) {
            OperationEvaluator operationEvaluator = new OperationEvaluator(table);
            node = operationEvaluator.evaluate(node);
        }

        return node;
    }

}
