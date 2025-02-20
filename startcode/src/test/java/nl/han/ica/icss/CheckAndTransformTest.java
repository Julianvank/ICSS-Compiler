package nl.han.ica.icss;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;
import nl.han.ica.icss.checker.Checker;
import nl.han.ica.icss.transforms.Evaluator;
import nl.han.ica.icss.transforms.Transform;
import org.junit.jupiter.api.Test;

public class CheckAndTransformTest {

    @Test
    void checkAndTransformStyleSheet() {
        AST ast = Level2();
        Checker checker = new Checker();
        Transform transform = new Evaluator();
        checker.check(ast);
        transform.apply(ast);
    }

    public static AST Level2() {
        Stylesheet stylesheet = new Stylesheet();
		/*
			ParWidth := 500px;
		 */

        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("ParWidth"))
                .addChild(new PixelLiteral("500px"))
        );
        /*
            #menu {
        	width: ParWidth + 2 * 10px;
            }
        */
        stylesheet.addChild((new Stylerule())
                .addChild(new IdSelector("#menu"))
                .addChild((new Declaration("width"))
                        .addChild((new AddOperation())
                                .addChild(new VariableReference("ParWidth"))
                                .addChild((new MultiplyOperation())
                                        .addChild(new ScalarLiteral("10"))
                                        .addChild((new SubtractOperation())
                                                .addChild(new PixelLiteral("50px"))
                                                .addChild(new PixelLiteral("40px"))
                                        )))));
        return new AST(stylesheet);
    }
}
