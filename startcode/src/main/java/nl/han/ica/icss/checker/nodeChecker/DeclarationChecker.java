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

    @Override
    public ASTNode checkNode(ASTNode node) {
        Declaration declaration = (Declaration) node;

        ExpressionChecker expressionChecker = new ExpressionChecker(symbolTable);
        Expression expression = expressionChecker.checkNode(declaration.expression);

        if(expression.hasError()) {
            node.setError(expression.getError().description);
            return node;
        }
//        declaration.expression = expression;

        switch(declaration.property.name){
            case "color" :
            case "background-color" :
                if(!checkColor(expression)){
                    node.setError("The given property should use an Colour. Anything other would be quite silly. You have given me: " + expression.getNodeLabel());
                }
                break;
            case "width" :
            case "height" :
                if(!checkProportion(expression)){
                    node.setError("The given property should use an Pixel or Percentage. Anything other would be quite silly. You have given me: " + expression.getNodeLabel());
                }
                break;
            default:
                node.setError("Declaration should be of specified property name: width, height, color, background-color. You provided: " + declaration.property.getNodeLabel());
        }

        return node;
    }

    private boolean checkProportion(Expression node) {
        return node instanceof PixelLiteral || node instanceof PercentageLiteral;
    }

    private boolean checkColor(Expression node) {
        return node instanceof ColorLiteral;
    }

    /*
        width: pixel || percentage
        height: pixel || percentage
        color: color
        background-color: color
     */
}
