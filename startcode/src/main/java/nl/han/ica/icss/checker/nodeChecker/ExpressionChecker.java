package nl.han.ica.icss.checker.nodeChecker;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.checker.SymbolTable;

public class ExpressionChecker extends NodeCheckerBase {
    public ExpressionChecker(ASTNode node, SymbolTable table) {
        super(node, table);
    }
}
