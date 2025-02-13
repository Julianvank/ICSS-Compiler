package nl.han.ica.icss.checker.nodeChecker;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.datastructures.SymbolTable;

public class DeclarationChecker extends NodeCheckerBase {

    public DeclarationChecker(SymbolTable symbolTable) {
        super(symbolTable);
    }
    private ExpressionChecker expressionChecker;

    @Override
    public ASTNode checkNode(ASTNode node) {
        Declaration declaration = (Declaration) node;

        expressionChecker = new ExpressionChecker(symbolTable);
        declaration.expression = (Expression) expressionChecker.checkNode(declaration.expression);
        if(declaration.expression.getError() != null) return node;

        switch(declaration.property.name){
            case "color" :
            case "background-color" :
                checkColor(declaration);
                return node;
            case "width" :
            case "height" :
                checkProportion(declaration);
                return node;

        }

        node.setError("Declaration should be of specified property name: width, height, color, background-color. You provided: " + declaration.property.getNodeLabel());
        return node;
    }

    private void checkProportion(Declaration node) {
        ASTNode expression = node.expression;
        if(expression instanceof PixelLiteral || expression instanceof PercentageLiteral) return;
        node.setError("The given property should use an Pixel or Percentage. Anything other would be quite silly. You have given me: " + node.expression.getNodeLabel());
    }

    private void checkColor(Declaration node) {
        ASTNode expression = node.expression;
        if(expression instanceof ColorLiteral) return;
        node.setError("The given property should use an Colour. Anything other would be quite silly. You have given me: " + node.expression.getNodeLabel());
    }

    /*
        width: pixel || percentage
        height: pixel || percentage
        color: color
        background-color: color
     */
}
