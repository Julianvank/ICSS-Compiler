package nl.han.ica.icss.transforms.NodeEvaluator;

import nl.han.ica.datastructures.*;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;

public class OperationEvaluator extends NodeEvaluatorBase {

    IHANQueue<Token> infix;
    IHANStack<Integer> postfix;
    IHANStack<Integer> operators;

    IHANStack<Integer> operands;
    ASTNode type;


    public OperationEvaluator(SymbolTable table) {
        super(table);

        operands = new HANStack<>();

        operators = new HANStack<>();
        operators.push(EOL);
        infix = new HANQueue<>();
        postfix = new HANStack<>();
    }

    @Override
    public ASTNode evaluate(ASTNode node) {
        getInfixExpression(node);
        infix.enqueue(new Token(EOL));


        Token lastToken;
        do {
            lastToken = infix.dequeue();
            processToken(lastToken);
        } while (lastToken.getType() != EOL);

//        return node;

//
        int value = postfix.peek();

        return createNewNode(type, value);

    }

    private void processToken(Token lastToken) {
        int topOperator;
        int lastType = lastToken.getType();

        if (lastType == VALUE) {
            postfix.push(lastToken.getValue());
        } else {
            while (precTable[lastType].inputSymbol <=
                    precTable[topOperator = operators.peek()].topOfStack)
                operateStack(topOperator);
            if (lastType != EOL)
                operators.push(lastType);
        }
    }


    private ASTNode createNewNode(ASTNode type, int value) {
        if (type instanceof PixelLiteral) return new PixelLiteral(value);
        if (type instanceof PercentageLiteral) return new PercentageLiteral(value);
        if (type instanceof ScalarLiteral) return new ScalarLiteral(value);
        return type;
    }

    private void getInfixExpression(ASTNode node) {
        node = findSymbol(node);
        if (node instanceof Operation) {
            Operation operation = (Operation) node;
            getInfixExpression(operation.lhs);
            infix.enqueue(getToken(node));
            getInfixExpression(operation.rhs);
        }
    }

    private ASTNode findSymbol(ASTNode node) {
        if (node instanceof VariableReference) node = table.findSymbol(((VariableReference) node).name);

        if (node instanceof Literal) {
            infix.enqueue(getToken(node));
            if (type == null || (type instanceof ScalarLiteral && !(node instanceof ScalarLiteral))) {
                type = node;
            }
        }
        return node;
    }

    private int getValue(ASTNode node) {
        if (node instanceof PixelLiteral) return ((PixelLiteral) node).value;
        if (node instanceof PercentageLiteral) return ((PercentageLiteral) node).value;
        if (node instanceof ScalarLiteral) return ((ScalarLiteral) node).value;
        return 0;
    }

    private void operateStack(int topOp) throws UnderflowException {
        int rhs = postfix.pop();
        int lhs = postfix.pop();
        System.out.print("operating with " + lhs + " and: " + rhs);
        switch (topOp) {
            case ADD:
                System.out.println(" ADD");
                postfix.push(lhs + rhs);
                break;
            case SUB:
                System.out.println(" SUB");
                postfix.push(lhs - rhs);
                break;
            case MUL:
                System.out.println(" MUL");
                postfix.push(lhs * rhs);
                break;
        }
        operators.pop();
    }

    private static final int VALUE = -1;
    private static final int EOL = 0;
    private static final int MUL = 1;
    private static final int ADD = 2;
    private static final int SUB = 3;

    private static class Precedence {
        public int inputSymbol;
        public int topOfStack;

        public Precedence(int inputSymbol, int topOfStack) {
            this.inputSymbol = inputSymbol;
            this.topOfStack = topOfStack;
        }
    }

    private static final Precedence[] precTable = {
            new Precedence(0, -1),
            new Precedence(3, 4),
            new Precedence(1, 2),
            new Precedence(1, 2)
    };

    private static int getPrecedence(ASTNode node) {
        if (node instanceof MultiplyOperation) return MUL;
        if (node instanceof AddOperation) return ADD;
        if (node instanceof SubtractOperation) return SUB;
        return -1;
    }

    public static class Token {
        private int type = 0;
        private int value = 0;

        public Token(int type) {
            this(type, 0);
        }

        public Token(int type, int value) {
            this.type = type;
            this.value = value;
        }

        public int getType() {
            return type;
        }

        public int getValue() {
            return value;
        }
    }

    public Token getToken(ASTNode node) {
        if (node instanceof MultiplyOperation) return new Token(MUL);
        if (node instanceof AddOperation) return new Token(ADD);
        if (node instanceof SubtractOperation) return new Token(SUB);

        return new Token(VALUE, getValue(node));
    }

}
