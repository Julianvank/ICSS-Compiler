package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;
import java.util.LinkedList;

public class Evaluator implements Transform {

    private IHANLinkedList<HashMap<String, Literal>> variableValues;

    public Evaluator() {
        //variableValues = new HANLinkedList<>();
    }

    @Override
    public void apply(AST ast) {
        //variableValues = new HANLinkedList<>();

        /*
         * traverse tree recursive
         * if expression getValue
         * replace expression with Lateral
         */

    }
    private void traverse(ASTNode node){
        if(node == null){
            return;
        }

        for(ASTNode child : node.getChildren()){
            if(node instanceof VariableAssignment){
                addVariable(node);
                return;
            }
            if(node instanceof Expression){
                replaceExpressionWithLiteral(node);
            }
            if(node instanceof IfClause){
                evaluateIfClause(node);
            }
        }


    }

    private void evaluateIfClause(ASTNode node) {
    }

    private void replaceExpressionWithLiteral(ASTNode node) {
//        if(node instanceof Operation){
//
//        }
//        if(node instanceof Literal){
//            return
//        }
    }

    private void addVariable(ASTNode node){
        //TODO if instance of expression. Get expressionValue.
        //TODO put name and value in LinkedList(Stack)
    }

//    private Literal getValue(ASTNode node) {
//
//    }
}
