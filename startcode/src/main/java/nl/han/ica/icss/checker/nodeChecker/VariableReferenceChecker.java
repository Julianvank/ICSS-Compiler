package nl.han.ica.icss.checker.nodeChecker;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.VariableReference;
import nl.han.ica.datastructures.SymbolTable;

public class VariableReferenceChecker extends NodeCheckerBase{
    public VariableReferenceChecker(SymbolTable table) {
        super(table);
    }

    @Override
    public ASTNode checkNode(ASTNode node) {
        String name = ((VariableReference) node).name;

        Expression expression = symbolTable.findSymbol(name);
        if(expression != null) {
            node = expression; return node;}

        node.setError("variable: " + name + " has not been declared");

        return node;
    }
}
