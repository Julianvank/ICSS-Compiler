package nl.han.ica.icss.checker.nodeChecker;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.IfClause;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.checker.SymbolTable;

public class IfClauseChecker extends NodeCheckerBase{

    private final boolean shouldPushScope = true;


    public IfClauseChecker(ASTNode node, SymbolTable table) {
        super(node, table);
    }

    public void checkNode() {
        IfClause newNode = (IfClause) node;

        if(! (newNode.conditionalExpression instanceof BoolLiteral)){
            node.setError("An ifClause expects a BoolLiteral as expression");
        }
        symbolTable.pushScope();
    }

    @Override
    public boolean isShouldPushScope() {
        return shouldPushScope;
    }
}
