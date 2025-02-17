package nl.han.ica.icss.transforms.NodeEvaluator;

import nl.han.ica.datastructures.SymbolTable;
import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.VariableReference;

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

//        if (node instanceof VariableReference) {
//            node = table.findSymbol(((VariableReference) node).name);
//        }
//
//        if(node instanceof Operation){
//            node = collapseOperation((Operation) node);
//        }

        return node;
    }

    private ASTNode collapseOperation(Operation node){
        ASTNode lhs = evaluate(node.lhs);
        ASTNode rhs = evaluate(node.rhs);

        //Create prefix expression.

        //Create postfix expression
        return node;
    }
}
