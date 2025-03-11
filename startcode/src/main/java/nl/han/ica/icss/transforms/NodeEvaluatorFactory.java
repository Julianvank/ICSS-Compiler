package nl.han.ica.icss.transforms;
import nl.han.ica.datastructures.SymbolTable;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.transforms.NodeEvaluator.*;

public class NodeEvaluatorFactory {

    public INodeEvaluator createNodeEvaluator(Evaluator evaluator, ASTNode node){
        SymbolTable table = evaluator.getSymbolTable();

        if(node instanceof Stylerule) return new StyleRuleEvaluator(table);
        if(node instanceof Expression) return new ExpressionEvaluator(table);
        if(node instanceof IfClause) return new IfClauseEvaluator(table);

        return new NodeEvaluatorBase(table);
    }

    public static boolean shouldScopeBePushed(ASTNode node){
        if(node instanceof IfClause) return true;
        if(node instanceof ElseClause) return true;
        if(node instanceof Stylerule) return true;
        return false;
    }
}
