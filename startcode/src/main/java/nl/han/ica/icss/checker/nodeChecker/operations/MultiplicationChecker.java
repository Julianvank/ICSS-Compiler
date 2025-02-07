package nl.han.ica.icss.checker.nodeChecker.operations;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.checker.SymbolTable;
import nl.han.ica.icss.checker.nodeChecker.OperationChecker;

public class MultiplicationChecker extends OperationChecker {
    public MultiplicationChecker(ASTNode node, SymbolTable table) {
        super(node, table);
    }

    @Override
    public void checkNode(){
       super.checkNode();
       Operation newNode = (Operation) node;

       if(!(newNode.lhs instanceof ScalarLiteral) || !(newNode.rhs instanceof ScalarLiteral)){
           node.setError("Must multiply with at least one Scalar");
       }
    }
}
