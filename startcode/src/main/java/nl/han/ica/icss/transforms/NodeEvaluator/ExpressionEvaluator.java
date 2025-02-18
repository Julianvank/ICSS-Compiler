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

    private ASTNode collapseOperation(Operation node) {
        ASTNode lhs = evaluate(node.lhs);
        ASTNode rhs = evaluate(node.rhs);

        if (node instanceof MultiplyOperation) {
            return multiplyNode(lhs, rhs);
        } else if (node instanceof AddOperation) {
            return addNode(lhs, (PixelLiteral) rhs);
        } else if (node instanceof SubtractOperation) {
            return subtractNode(lhs, (PixelLiteral) rhs);
        }
        return node;
    }

    private static ASTNode subtractNode(ASTNode lhs, PixelLiteral rhs) {
        return lhs instanceof PixelLiteral ?
                new PixelLiteral(((PixelLiteral) lhs).value - rhs.value) :
                new PercentageLiteral(((PercentageLiteral) lhs).value - rhs.value);
    }

    private static ASTNode addNode(ASTNode lhs, PixelLiteral rhs) {
        return lhs instanceof PixelLiteral ?
                new PixelLiteral(((PixelLiteral) lhs).value + rhs.value) :
                new PercentageLiteral(((PercentageLiteral) lhs).value + rhs.value);
    }

    private static ASTNode multiplyNode(ASTNode lhs, ASTNode rhs) {
        if (lhs instanceof ScalarLiteral && rhs instanceof ScalarLiteral) {
            return new ScalarLiteral(((ScalarLiteral) lhs).value * ((ScalarLiteral) rhs).value);
        } else if (lhs instanceof ScalarLiteral) {
            return rhs instanceof PixelLiteral ?
                    new PixelLiteral(((ScalarLiteral) lhs).value * ((PixelLiteral) rhs).value) :
                    new PercentageLiteral(((ScalarLiteral) lhs).value * ((PercentageLiteral) rhs).value);
        }
        return null;
    }
}
