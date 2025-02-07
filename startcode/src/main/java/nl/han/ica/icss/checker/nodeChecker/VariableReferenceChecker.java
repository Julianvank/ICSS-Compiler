package nl.han.ica.icss.checker.nodeChecker;

import nl.han.ica.datastructures.LinkedListIterator;
import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.VariableReference;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.SymbolTable;

import java.util.HashMap;
import java.util.Stack;

public class VariableReferenceChecker extends NodeCheckerBase{
    public VariableReferenceChecker(ASTNode node, SymbolTable table) {
        super(node, table);
    }

    @Override
    public void checkNode() {
        String name = ((VariableReference) node).name;
        SymbolTable copyTable = new SymbolTable(symbolTable.getSymbolTable());

        //Look at each layer of the stack
        for(; copyTable.getSymbolTable().isEmpty(); copyTable.popScope()){
            if(copyTable.getTop().containsKey(name)){
                return;
            }
        }
        node.setError("variable: " + name + "has not been declared");

//        for (; copyTable.getSymbolTable().isEmpty(); itr.advance()) {
//            if (itr.retrieve() != null && itr.retrieve().containsKey(name)) {
//                return;
//            }
//        }
//        node.setError("Variable not set");
    }
}
