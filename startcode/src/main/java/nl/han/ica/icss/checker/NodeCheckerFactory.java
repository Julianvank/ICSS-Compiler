package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.checker.nodeChecker.*;
import nl.han.ica.icss.checker.nodeChecker.operations.AdditionChecker;
import nl.han.ica.icss.checker.nodeChecker.operations.MultiplicationChecker;
import nl.han.ica.icss.checker.nodeChecker.operations.SubtractionChecker;

public class NodeCheckerFactory{

    public NodeCheckerBase createNodeChecker(Checker checker, ASTNode node){
        SymbolTable table = checker.getSymbolTable();
        if(node instanceof Literal) return null;
        if(node instanceof VariableReference) return new VariableReferenceChecker(node, table);
        if(node instanceof VariableAssignment) return new VariableAssignmentChecker(node, table);
        if(node instanceof IfClause) return new IfClauseChecker(node, table);

        if(node instanceof AddOperation) return new AdditionChecker(node, table);
        if(node instanceof SubtractOperation) return new SubtractionChecker(node, table);
        if(node instanceof MultiplyOperation) return new MultiplicationChecker(node, table);

        if(node instanceof Expression) return new ExpressionChecker(node, table);

        return null;
    }

    public boolean shouldScopeBePushed(ASTNode node){
        if(node instanceof IfClause) return true;
        if(node instanceof ElseClause) return true;
        if(node instanceof Stylerule) return true;
        return false;
    }

}
