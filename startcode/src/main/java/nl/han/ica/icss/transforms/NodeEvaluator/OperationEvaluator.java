package nl.han.ica.icss.transforms.NodeEvaluator;

import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.SymbolTable;
import nl.han.ica.datastructures.UnderflowException;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;

public class OperationEvaluator extends NodeEvaluatorBase {

    HANStack<Integer> operands;
    HANStack<Integer> operators;
    ASTNode type;


    public OperationEvaluator(SymbolTable table) {
        super(table);

        operands = new HANStack<>();
        operators = new HANStack<>();
    }

    @Override
    public ASTNode evaluate(ASTNode node) {
        //traverseNode(node);
        /*
           traverseNode(node)
           while(operators.isValid()){
             operateStack()
           }
           int value = operands.top();

           return createNewNode(type, value);
         */

        try {
            traverseNode(node);

            while(!operators.isEmpty()){
                operateStack(operators.peek());
            }

            int value = operands.peek();

            return createNewNode(type, value);

        } catch (UnderflowException e) {
            throw new RuntimeException(e);
        }
    }

    private ASTNode createNewNode(ASTNode type, int value){
        if(type instanceof PixelLiteral) return new PixelLiteral(value);
        if(type instanceof PercentageLiteral) return new PercentageLiteral(value);
        if(type instanceof ScalarLiteral) return new ScalarLiteral(value);
        return type;
    }

    private void traverseNode(ASTNode node) throws UnderflowException {
        if (node instanceof VariableReference) node = table.findSymbol(((VariableReference) node).name);
        if (node instanceof Literal) {
            operands.push(getValue(node));
            if(type == null || (type instanceof ScalarLiteral && !(node instanceof ScalarLiteral))){
                type = node;
            }
        }
        if (node instanceof Operation) {
            Operation operation = (Operation) node;
            ASTNode lhs = operation.lhs;
            ASTNode rhs = operation.rhs;

            traverseNode(rhs);
            int precedence = getPrecedence(operation);
            if (precTable[precedence].inputSymbol <= precTable[operators.peek()].topOfStack) {
                while (precTable[precedence].inputSymbol <= precTable[operators.peek()].topOfStack) {
                    operateStack(operators.peek());
                }
            } else {
                operators.push(precedence);
            }
            traverseNode(lhs);
        }
    }

    private int getValue(ASTNode node) {
        if (node instanceof PixelLiteral) return ((PixelLiteral) node).value;
        if (node instanceof PercentageLiteral) return ((PercentageLiteral) node).value;
        if (node instanceof ScalarLiteral) return ((ScalarLiteral) node).value;
        return 0;
    }

    private void operateStack(int topOp) throws UnderflowException {
        int rhs = operands.pop();
        int lhs = operands.pop();

        switch(topOp){
            case ADD :
                operands.push(lhs + rhs);
                break;
            case SUB :
                operands.push(lhs - rhs);
                break;
            case MUL :
                operands.push(lhs * rhs);
                break;
        }
        operators.pop();
    }

    private static final int MUL = 0;
    private static final int ADD = 1;
    private static final int SUB = 2;

    private static class Precedence {
        public int inputSymbol;
        public int topOfStack;

        public Precedence(int inputSymbol, int topOfStack){
            this.inputSymbol = inputSymbol;
            this.topOfStack = topOfStack;
        }
    }

    private static final Precedence [] precTable = {
            new Precedence(3, 4),
            new Precedence(1, 2),
            new Precedence(1, 2)
    };

    private static int getPrecedence(ASTNode node){
        if(node instanceof MultiplyOperation) return MUL;
        if(node instanceof AddOperation) return ADD;
        if(node instanceof SubtractOperation) return SUB;
        return -1;
    }

}
