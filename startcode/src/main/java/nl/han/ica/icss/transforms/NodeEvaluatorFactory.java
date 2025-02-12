package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.checker.Checker;
import nl.han.ica.icss.checker.SymbolTable;
import nl.han.ica.icss.checker.nodeChecker.*;
import nl.han.ica.icss.checker.nodeChecker.operations.AdditionChecker;
import nl.han.ica.icss.checker.nodeChecker.operations.MultiplicationChecker;
import nl.han.ica.icss.checker.nodeChecker.operations.SubtractionChecker;
import nl.han.ica.icss.transforms.nodeEvaluators.ExpressionEvaluator;
import nl.han.ica.icss.transforms.nodeEvaluators.IfClauseEvaluator;
import nl.han.ica.icss.transforms.nodeEvaluators.VariableAssignmentEvaluator;

public class NodeEvaluatorFactory {

    public NodeCheckerBase createNodeEvaluator(Evaluator evaluator, ASTNode node){
        SymbolTable table = evaluator.getSymbolTable();
        if(node instanceof VariableAssignment) return new VariableAssignmentEvaluator(node, table);
        if(node instanceof IfClause) return new IfClauseEvaluator(node, table);
        if(node instanceof Literal) return null;
        if(node instanceof Expression) return new ExpressionEvaluator(node, table);

        return null;
    }

    public boolean shouldScopeBePushed(ASTNode node){
        if(node instanceof IfClause) return true;
        if(node instanceof ElseClause) return true;
        if(node instanceof Stylerule) return true;
        return false;
    }

}
