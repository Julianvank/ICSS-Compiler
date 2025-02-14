package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.SymbolTable;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.checker.nodeChecker.*;

import nl.han.ica.icss.checker.nodeChecker.OperationChecker;

public class NodeCheckerFactory{

    public NodeCheckerBase createNodeChecker(Checker checker, ASTNode node){
        SymbolTable table = checker.getSymbolTable();
        if(node instanceof Literal) return null;
        if(node instanceof VariableReference) return new VariableReferenceChecker(table);
        if(node instanceof VariableAssignment) return new VariableAssignmentChecker(table);
        if(node instanceof IfClause) return new IfClauseChecker(table);

        if(node instanceof Operation) return new OperationChecker(table);

        if(node instanceof Expression) return new ExpressionChecker(table);
        if(node instanceof Declaration) return new DeclarationChecker(table);

        return null;
    }

    public static boolean shouldScopeBePushed(ASTNode node){
        if(node instanceof IfClause) return true;
        if(node instanceof ElseClause) return true;
        if(node instanceof Stylerule) return true;
        return false;
    }

}
