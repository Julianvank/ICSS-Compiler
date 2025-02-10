package nl.han.ica.icss.checker.nodeChecker.operations;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.checker.SymbolTable;

public class AdditionChecker extends OperationChecker {
    public AdditionChecker(ASTNode node, SymbolTable table) {
        super(node, table);
    }

    @Override
    public ASTNode checkNode(){
        super.checkNode();
        if(node.hasError()) return node;

        Operation newNode = (Operation) node;

        if(lhs.getClass() == rhs.getClass()){
            node = collapseOperation(lhs, rhs);
            return node;
        }

        node.setError("Addition needs to be done with the same Literal, now it's " + newNode.lhs.getNodeLabel() + " + " + newNode.rhs.getNodeLabel());
        return node;
    }

    @Override
    protected Literal collapseOperation(ASTNode lhs, ASTNode rhs) {
        if(lhs instanceof PixelLiteral){
            return new PixelLiteral((((PixelLiteral) lhs).value) + (((PixelLiteral) rhs).value));
        }

        if(lhs instanceof PercentageLiteral){
            return new PercentageLiteral((((PercentageLiteral) lhs).value) + (((PercentageLiteral) rhs).value));
        }

        return null;
    }
}
