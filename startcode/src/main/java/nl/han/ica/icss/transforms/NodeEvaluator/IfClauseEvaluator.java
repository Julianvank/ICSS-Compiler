package nl.han.ica.icss.transforms.NodeEvaluator;

import nl.han.ica.datastructures.SymbolTable;
import nl.han.ica.icss.ast.ASTNode;

public class IfClauseEvaluator extends NodeEvaluatorBase{

    public IfClauseEvaluator(SymbolTable table){
        super(table);
    }

    @Override
    public ASTNode evaluate(ASTNode node) {
        return super.evaluate(node);
    }
}
