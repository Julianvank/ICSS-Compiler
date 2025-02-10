package nl.han.ica.icss.checker.nodeChecker;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.checker.NodeCheckerFactory;
import nl.han.ica.icss.checker.SymbolTable;
import nl.han.ica.icss.checker.nodeChecker.operations.AdditionChecker;
import nl.han.ica.icss.checker.nodeChecker.operations.MultiplicationChecker;
import nl.han.ica.icss.checker.nodeChecker.operations.OperationChecker;
import nl.han.ica.icss.checker.nodeChecker.operations.SubtractionChecker;

import java.util.HashMap;

public class DeclarationChecker extends NodeCheckerBase {

    public DeclarationChecker(ASTNode node, SymbolTable symbolTable) {
        super(node, symbolTable);
    }

    @Override
    public ASTNode checkNode() {
        Declaration newNode = (Declaration) node;

        Expression expression = newNode.expression;

        if(expression instanceof VariableReference) {
            Expression varExpression = symbolTable.findSymbol(((VariableReference) expression).name);
            if (varExpression == null) {
                return node;
            }
            newNode.expression = varExpression;
        }

        if(expression instanceof Operation){
            newNode.expression = (Expression) nodeCheckerFactory(expression).checkNode();
        }

        switch(newNode.property.name){
            case "color" :
            case "background-color" :
                checkColor(newNode);
                return node;
            case "width" :
            case "height" :
                checkProportion(newNode);
                return node;

        }

        node.setError("Declaration should be of specified property name: width, height, color, background-color. You provided: " + newNode.property.getNodeLabel());
        return node;
    }

    private void checkProportion(Declaration newNode) {
        ASTNode expression = newNode.expression;
        if(expression instanceof PixelLiteral || expression instanceof PercentageLiteral) return;
        node.setError("The given property should use an Pixel or Percentage. Anything other would be quite silly. You have given me: " + newNode.expression.getNodeLabel());
    }

    private void checkColor(Declaration newNode) {
        ASTNode expression = newNode.expression;
        if(expression instanceof ColorLiteral) return;
        node.setError("The given property should use an Colour. Anything other would be quite silly. You have given me: " + newNode.expression.getNodeLabel());
    }

    private OperationChecker nodeCheckerFactory(ASTNode node){
        if(node instanceof AddOperation) return new AdditionChecker(node, symbolTable);
        if(node instanceof SubtractOperation) return new SubtractionChecker(node, symbolTable);
        if(node instanceof MultiplyOperation) return new MultiplicationChecker(node, symbolTable);

        return null;
    }

    /*
        width: pixel || percentage
        height: pixel || percentage
        color: color
        background-color: color
     */
}
