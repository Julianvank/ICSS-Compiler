package nl.han.ica.icss.transforms.NodeEvaluator;

import nl.han.ica.datastructures.SymbolTable;
import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.IfClause;
import nl.han.ica.icss.ast.literals.BoolLiteral;

import java.util.ArrayList;
import java.util.List;

public class IfClauseEvaluator extends NodeEvaluatorBase {

    public IfClauseEvaluator(SymbolTable table) {
        super(table);
    }

    @Override
    public ASTNode evaluate(ASTNode node) {
        IfClause clause = (IfClause) node;
        boolean condition = getValue(clause.conditionalExpression);

        if(!condition) {
            clause.body = clause.elseClause.body;
        }
        return clause;
    }

    public List<ASTNode> getBody(IfClause node){
        List<ASTNode> body = new ArrayList<>();
        boolean condition = getValue(node.conditionalExpression);

        if(condition){
            body = node.body;
        } else if (node.elseClause != null) {
//            table.moveUp(node.elseClause);
            body = node.elseClause.body;
        }
        return body;
    }

    private boolean getValue(Expression expression) {
        ExpressionEvaluator evaluator = new ExpressionEvaluator(table);
        BoolLiteral value = (BoolLiteral) evaluator.evaluate(expression);
        return value.value;
    }
}
