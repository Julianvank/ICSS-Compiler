package nl.han.ica.icss.checker.nodeChecker.operations;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.checker.SymbolTable;

public class MultiplicationChecker extends OperationChecker {
    public MultiplicationChecker(ASTNode node, SymbolTable table) {
        super(node, table);
    }

    @Override
    public ASTNode checkNode() {
        super.checkNode();
        if(node.hasError()) return node;

        Operation newNode = (Operation) node;

        if ((newNode.lhs instanceof ScalarLiteral) ) {
            node = collapseOperation(newNode.lhs, newNode.rhs);
            return node;
        }else if((newNode.rhs instanceof ScalarLiteral)){
            node = collapseOperation(newNode.rhs, newNode.lhs);
            return node;
        }

        node.setError("Must multiply with at least one Scalar");
        return node;
    }

    @Override
    protected Literal collapseOperation(ASTNode lhs, ASTNode rhs){
        ScalarLiteral scalarNode;
        ASTNode valueNode;

        if(lhs instanceof ScalarLiteral){
            scalarNode = (ScalarLiteral) lhs;
            valueNode = rhs;
        }else{
            scalarNode = (ScalarLiteral) rhs;
            valueNode = lhs;
        }

        if(valueNode instanceof PercentageLiteral){
            return new PercentageLiteral(
                    ((PercentageLiteral) valueNode).value * scalarNode.value
            );
        }
        if(valueNode instanceof PixelLiteral){
            return new PixelLiteral(
                    ((PixelLiteral) valueNode).value * scalarNode.value
            );
        }

        return new ScalarLiteral(((ScalarLiteral) valueNode).value * scalarNode.value);
    }
}
