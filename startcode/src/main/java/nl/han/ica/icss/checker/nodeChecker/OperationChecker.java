package nl.han.ica.icss.checker.nodeChecker;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.datastructures.SymbolTable;
import nl.han.ica.icss.checker.nodeChecker.ExpressionChecker;
import nl.han.ica.icss.checker.nodeChecker.NodeCheckerBase;

public class OperationChecker extends NodeCheckerBase {
    public OperationChecker(SymbolTable table) {
        super(table);
        expressionChecker = new ExpressionChecker(table);
    }
    ExpressionChecker expressionChecker;



    @Override
    public ASTNode checkNode(ASTNode node){
        Operation operation = (Operation) node;

        ASTNode lhs;
        ASTNode rhs;

//        if(operation.lhs instanceof Operation) {
//            lhs = checkNode(operation.lhs);
//        }else {
            lhs = expressionChecker.checkNode(operation.lhs);
//        }

        if(operation.rhs instanceof Operation) {
            rhs = ((Operation) operation.rhs).lhs;
//            rhs = checkNode(operation.rhs);
        }else {
            rhs = expressionChecker.checkNode(operation.rhs);
        }


        if((operation.lhs instanceof ColorLiteral) || (operation.rhs instanceof ColorLiteral)){
            node.setError("Colour can't be used in operations");
            return node;
        }

        if((operation.lhs instanceof BoolLiteral) || (operation.rhs instanceof BoolLiteral)){
            node.setError("Boolean can't be used in operations");
            return node;
        }

        if(operation instanceof MultiplyOperation){

            if(!(lhs instanceof ScalarLiteral) && !(rhs instanceof ScalarLiteral)){
                node.setError("A scalar must be used in multiplication.");
                return node;
            }
            return !(rhs instanceof ScalarLiteral) ? rhs : lhs;
        } else if ((operation instanceof AddOperation || operation instanceof SubtractOperation) && lhs.getClass() != rhs.getClass()){
            node.setError("Subtraction and Multiplication must be done with same Literals");
            return node;
        }


//        return lhs;
        return lhs instanceof PixelLiteral || lhs instanceof PercentageLiteral ? lhs : rhs;
    }

//    public ASTNode evaluateExpression(ASTNode node) {
//        if (node instanceof Operation) {
//            Operation operation = (Operation) node;
//            ASTNode lhs = evaluateExpression(operation.lhs);
//            ASTNode rhs = evaluateExpression(operation.rhs);
//
//            return nodeCheckerFactory(node).collapseOperation(lhs, rhs);
//        } else{
//            return node;
//        }
//    }
//
//    private OperationChecker nodeCheckerFactory(ASTNode node){
//        if(node instanceof AddOperation) return new AdditionChecker(symbolTable);
//        if(node instanceof SubtractOperation) return new SubtractionChecker(symbolTable);
//        if(node instanceof MultiplyOperation) return new MultiplicationChecker(symbolTable);
//
//        return null;
//    }
//    protected abstract Literal collapseOperation(ASTNode lhs, ASTNode rhs);
}
