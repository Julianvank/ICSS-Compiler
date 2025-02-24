package nl.han.ica.icss.evaluator;

import nl.han.ica.datastructures.SymbolTable;
import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.transforms.Evaluator;
import nl.han.ica.icss.transforms.NodeEvaluator.OperationEvaluator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OperationEvaluatorTest {
    Evaluator evaluator;

    OperationEvaluator sut;
    SymbolTable table;

    @BeforeEach
    void setUp() {
        this.table = new SymbolTable();
        this.sut = new OperationEvaluator(table);
    }

    @Test
    void singleAddPixel() {
        int expectedValue = 100;
        ASTNode testNode =
                new AddOperation()
                        .addChild(new PixelLiteral(50))
                        .addChild(new PixelLiteral(50));
        ASTNode actual = sut.evaluate(testNode);

        assertTrue(actual instanceof PixelLiteral);
        assertEquals(expectedValue, getValue(actual));
    }

    @Test
    void singleSubPixel() {
        int expectedValue = 10;
        ASTNode testNode =
                new SubtractOperation()
                        .addChild(new PixelLiteral(50))
                        .addChild(new PixelLiteral(40));
        ASTNode actual = sut.evaluate(testNode);

        assertTrue(actual instanceof PixelLiteral);
        assertEquals(expectedValue, getValue(actual));
    }

    @Test
    void singleMulPixel() {
        int expectedValue = 50;
        ASTNode testNode =
                new MultiplyOperation()
                        .addChild(new PixelLiteral(25))
                        .addChild(new ScalarLiteral(2));
        ASTNode actual = sut.evaluate(testNode);

        assertTrue(actual instanceof PixelLiteral);
        assertEquals(expectedValue, getValue(actual));
    }

    @Test
    void singleAddPercentage() {
        int expectedValue = 75;
        ASTNode testNode =
                new AddOperation()
                        .addChild(new PercentageLiteral(50))
                        .addChild(new PercentageLiteral(25));
        ASTNode actual = sut.evaluate(testNode);

        assertTrue(actual instanceof PercentageLiteral);
        assertEquals(expectedValue, getValue(actual));
    }

    @Test
    void singleSubPercentage() {
        int expectedValue = 25;
        ASTNode testNode =
                new SubtractOperation()
                        .addChild(new PercentageLiteral(50))
                        .addChild(new PercentageLiteral(25));
        ASTNode actual = sut.evaluate(testNode);

        assertTrue(actual instanceof PercentageLiteral);
        assertEquals(expectedValue, getValue(actual));
    }

    @Test
    void singleMulPercentage() {
        int expectedValue = 50;
        ASTNode testNode =
                new MultiplyOperation()
                        .addChild(new PercentageLiteral(25))
                        .addChild(new ScalarLiteral(2));
        ASTNode actual = sut.evaluate(testNode);

        assertTrue(actual instanceof PercentageLiteral);
        assertEquals(expectedValue, getValue(actual));
    }

    @Test
    void singleMulScalar() {
        int expectedValue = 16;
        ASTNode testNode =
                new MultiplyOperation()
                        .addChild(new ScalarLiteral(4))
                        .addChild(new ScalarLiteral(4));
        ASTNode actual = sut.evaluate(testNode);

        assertTrue(actual instanceof ScalarLiteral);
        assertEquals(expectedValue, getValue(actual));
    }

    @Test
    void doubleMulPlusPixel() {
        int expectedValue = 200;
        ASTNode testNode =
                new AddOperation()
                        .addChild((new MultiplyOperation())
                                .addChild(new PixelLiteral(50))
                                .addChild(new ScalarLiteral(2)))
                        .addChild((new MultiplyOperation())
                                .addChild(new PixelLiteral(50))
                                .addChild(new ScalarLiteral(2)));
        ASTNode actual = sut.evaluate(testNode);

        assertTrue(actual instanceof PixelLiteral);
        assertEquals(expectedValue, getValue(actual));
    }

    @Test
    void doubleMulPlusPercentage() {
        int expectedValue = 200;
        ASTNode testNode =
                new AddOperation()
                        .addChild((new MultiplyOperation())
                                .addChild(new PercentageLiteral(50))
                                .addChild(new ScalarLiteral(2)))
                        .addChild((new MultiplyOperation())
                                .addChild(new PercentageLiteral(50))
                                .addChild(new ScalarLiteral(2)));
        ASTNode actual = sut.evaluate(testNode);

        assertTrue(actual instanceof PercentageLiteral);
        assertEquals(expectedValue, getValue(actual));
    }

    @Test
    void complicatedPercentage() {
        int expectedValue = 130;
        ASTNode testNode =
                new MultiplyOperation()
                        .addChild(new PercentageLiteral(50))
                        .addChild(new AddOperation()
                                .addChild(new ScalarLiteral(2))
                                .addChild(new SubtractOperation()
                                        .addChild(new PercentageLiteral(50))
                                        .addChild(new MultiplyOperation()
                                                .addChild(new PercentageLiteral(10))
                                                .addChild(new ScalarLiteral(2)))));
        ASTNode actual = sut.evaluate(testNode);

        assertTrue(actual instanceof PercentageLiteral);
        assertEquals(expectedValue, getValue(actual));
    }


    private int getValue(ASTNode node) {
        if (node instanceof PixelLiteral) return ((PixelLiteral) node).value;
        if (node instanceof PercentageLiteral) return ((PercentageLiteral) node).value;
        if (node instanceof ScalarLiteral) return ((ScalarLiteral) node).value;
        return 0;
    }
}
