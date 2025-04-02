package nl.han.ica.icss.evaluator;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.selectors.TagSelector;
import nl.han.ica.icss.checker.Checker;
import nl.han.ica.icss.transforms.Evaluator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IfClauseEvaluatorTest {

    Evaluator sut;

    @Test
    void ifClauseEvaluatorTest_WhenGivenTrueStatement_ShouldReturnBody() {
        Evaluator sut = new Evaluator();
        Checker checker = new Checker();

        AST expected = IfClauseStatement_TrueBody_Expected();

        AST actual = IfClauseStatement_TrueBody_Input();
        checker.check(actual);
        sut.apply(actual);

        Assertions.assertEquals(expected, actual);
    }

    public static AST IfClauseStatement_TrueBody_Input() {
        Stylesheet stylesheet = new Stylesheet();
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new Declaration("background-color"))
                        .addChild(new ColorLiteral("#ffffff")))
                .addChild((new IfClause())
                        .addChild(new BoolLiteral(true))
                        .addChild(new Declaration("width")
                                .addChild(new PixelLiteral(50)))
                )
        );
        return new AST(stylesheet);
    }

    public static AST IfClauseStatement_TrueBody_Expected() {
        Stylesheet stylesheet = new Stylesheet();
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new Declaration("background-color"))
                        .addChild(new ColorLiteral("#ffffff")))
                .addChild((new Declaration("width")
                        .addChild(new PixelLiteral(50)))
                )
        );
        return new AST(stylesheet);
    }

    @Test
    void ifClauseEvaluatorTest_WhenGivenFalseStatement_ShouldReturnElseIfExists() {

    }

    @Test
    void ifClauseEvaluatorTest_WhenGivenFalseStatement_ShouldReturnEmptyIfElseNotExists() {

    }
}
