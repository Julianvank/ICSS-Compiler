package nl.han.ica.icss.checker.nodeChecker.operations;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.checker.SymbolTable;
import nl.han.ica.icss.checker.nodeChecker.NodeCheckerBase;

public class AdditionChecker extends NodeCheckerBase {
    public AdditionChecker(ASTNode node, SymbolTable table) {
        super(node, table);
    }

    @Override
    public void checkNode(){
        super.checkNode();
        Operation newNode = (Operation) node;

        if(newNode.lhs.getClass() != newNode.rhs.getClass()){
            node.setError("Addition needs to be done with the same Literal");
        }
    }
}
