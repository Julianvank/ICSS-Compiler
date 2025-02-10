package nl.han.ica.icss.checker.nodeChecker;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.VariableAssignment;
import nl.han.ica.icss.checker.SymbolTable;

public class VariableAssignmentChecker extends NodeCheckerBase {

    public VariableAssignmentChecker(ASTNode node, SymbolTable table) {
        super(node, table);
    }

    @Override
    public ASTNode checkNode(){
        VariableAssignment varAss = (VariableAssignment) node;
        String name = varAss.name.name;
        Expression expression = varAss.expression;

        //TODO check of declaraties een logisch type hebben.
        symbolTable.addSymbol(name, expression);

        return node;
    }
}
