package nl.han.ica.icss.checker.nodeChecker.operations;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.checker.NodeCheckerFactory;
import nl.han.ica.icss.checker.SymbolTable;
import nl.han.ica.icss.checker.nodeChecker.NodeCheckerBase;

public abstract class OperationChecker extends NodeCheckerBase {
    protected OperationChecker(ASTNode node, SymbolTable table) {
        super(node, table);
    }

    protected ASTNode lhs;
    protected ASTNode rhs;

    @Override
    public ASTNode checkNode(){
        Operation newNode = (Operation) node;



        if(newNode.lhs instanceof VariableReference){
            newNode.lhs = symbolTable.findSymbol(((VariableReference) newNode.lhs).name);
        }
        if(newNode.rhs instanceof VariableReference){
            newNode.rhs = symbolTable.findSymbol(((VariableReference) newNode.rhs).name);
        }

        if((newNode.lhs instanceof ColorLiteral) || (newNode.rhs instanceof ColorLiteral)){
            node.setError("Colour can't be used in operations");
        }

        if((newNode.lhs instanceof BoolLiteral) || (newNode.rhs instanceof BoolLiteral)){
            node.setError("Boolean can't be used in operations");
        }

        lhs = evaluateExpression(newNode.lhs);
        rhs = evaluateExpression(newNode.rhs);


        return node;


    }

    private ASTNode evaluateExpression(ASTNode node) {
        if (node instanceof Operation) {
            Operation operation = (Operation) node;
            ASTNode lhs = evaluateExpression(operation.lhs);
            ASTNode rhs = evaluateExpression(operation.rhs);

            return nodeCheckerFactory(node).collapseOperation(lhs, rhs);
        } else{
            return node;
        }
    }

    private OperationChecker nodeCheckerFactory(ASTNode node){
        if(node instanceof AddOperation) return new AdditionChecker(node, symbolTable);
        if(node instanceof SubtractOperation) return new SubtractionChecker(node, symbolTable);
        if(node instanceof MultiplyOperation) return new MultiplicationChecker(node, symbolTable);

        return null;
    }
    protected abstract Literal collapseOperation(ASTNode lhs, ASTNode rhs);
}
