package nl.han.ica.icss.evaluator;

import nl.han.ica.datastructures.SymbolTable;
import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Declaration;
import nl.han.ica.icss.ast.IfClause;
import nl.han.ica.icss.ast.Stylerule;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.selectors.TagSelector;
import nl.han.ica.icss.transforms.NodeEvaluator.IfClauseEvaluator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IfClauseEvaluatorTest {

    IfClauseEvaluator sut;

    @Test
    void ifClauseEvaluatorTest_WhenGivenTrueStatement_ShouldReturnBody() {
        sut = new IfClauseEvaluator(new SymbolTable());
        ASTNode testNode =
                (new Stylerule())
                        .addChild(new TagSelector("p"))
                        .addChild((new IfClause())
                                .addChild(new PixelLiteral(50))
                                .addChild((new Declaration("color")
                                        .addChild(new ColorLiteral("#124532"))))
                        );
        ASTNode expected =
                (new Stylerule())
                        .addChild(new TagSelector("p"))
                        .addChild(new PixelLiteral(50))
                        .addChild((new Declaration("color")
                                .addChild(new ColorLiteral("#124532"))));
        ASTNode actual = sut.evaluate(testNode);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void ifClauseEvaluatorTest_WhenGivenFalseStatement_ShouldReturnElseIfExists() {

    }

    @Test
    void ifClauseEvaluatorTest_WhenGivenFalseStatement_ShouldReturnEmptyIfElseNotExists() {

    }
}
