package nl.han.ica.icss.checker.nodeChecker;

import nl.han.ica.datastructures.LinkedListIterator;
import nl.han.ica.icss.ast.AST;
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
    public ASTNode checkNode() {
        String name = ((VariableReference) node).name;

        Expression expression = symbolTable.findSymbol(name);
        if(expression != null) {node = expression; return node;}

        node.setError("variable: " + name + " has not been declared");

        return node;
    }
}
