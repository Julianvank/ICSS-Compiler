package nl.han.ica.icss.checker.nodeChecker;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.checker.SymbolTable;

public abstract class OperationChecker extends NodeCheckerBase{
    protected OperationChecker(ASTNode node, SymbolTable table) {
        super(node, table);
    }

    @Override
    public void checkNode(){
        Operation newNode = (Operation) node;

        if((newNode.lhs instanceof ColorLiteral) || newNode.rhs instanceof ColorLiteral){
            newNode.setError("Colour can't be used in operations");
            return;
        }
    }

}
