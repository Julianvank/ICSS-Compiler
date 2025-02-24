package nl.han.ica.icss.checker.nodeChecker;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.VariableAssignment;
import nl.han.ica.datastructures.SymbolTable;

public class VariableAssignmentChecker extends NodeCheckerBase {

    public VariableAssignmentChecker(SymbolTable table) {
        super(table);
    }

    @Override
    public ASTNode checkNode(ASTNode node){
        VariableAssignment varAss = (VariableAssignment) node;
        String name = varAss.name.name;
        Expression expression = varAss.expression;

        symbolTable.addSymbol(name, expression);

        return node;
    }
}
