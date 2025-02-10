package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;

public class CheckerFixtures {

    public static AST VariableDefinition_true() {
        Stylesheet stylesheet = new Stylesheet();
        /*
			LinkColor := #ff0000;
			ParWidth := 500px;
			AdjustColor := TRUE;
			UseLinkColor := FALSE;
		 */
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("LinkColor"))
                .addChild(new ColorLiteral("#ff0000"))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("ParWidth"))
                .addChild(new PixelLiteral("500px"))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("AdjustColor"))
                .addChild(new BoolLiteral(true))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("UseLinkColor"))
                .addChild(new BoolLiteral(false))
        );

   	    /*
	        p {
	        background-color: #ffffff;
	        width: ParWidth;
            }
	    */
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new Declaration("background-color"))
                        .addChild(new ColorLiteral("#ffffff")))
                .addChild((new Declaration("width"))
                        .addChild(new VariableReference("ParWidth")))
        );

        return new AST(stylesheet);
    }

    public static AST VariableDefinition_false() {
        Stylesheet stylesheet = new Stylesheet();
        /*
			LinkColor := #ff0000;
			AdjustColor := TRUE;
			UseLinkColor := FALSE;
		 */
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("LinkColor"))
                .addChild(new ColorLiteral("#ff0000"))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("AdjustColor"))
                .addChild(new BoolLiteral(true))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("UseLinkColor"))
                .addChild(new BoolLiteral(false))
        );

   	    /*
	        p {
	        background-color: #ffffff;
	        width: ParWidth;
            }
	    */
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new Declaration("background-color"))
                        .addChild(new ColorLiteral("#ffffff")))
                .addChild((new Declaration("width"))
                        .addChild(new VariableReference("ParWidth")))
        );

        return new AST(stylesheet);
    }

    public static AST addAndSubSameType_true() {
        Stylesheet stylesheet = new Stylesheet();
        /*
            WidthVar := 50px;
			LinkColor := #ff0000;
			AdjustColor := TRUE;
			UseLinkColor := FALSE;
		 */
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("WidthVar"))
                .addChild(new PixelLiteral("50px"))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("LinkColor"))
                .addChild(new ColorLiteral("#ff0000"))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("AdjustColor"))
                .addChild(new BoolLiteral(true))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("UseLinkColor"))
                .addChild(new BoolLiteral(false))
        );

   	    /*
	        p {
	        background-color: #ffffff;
	        width: ParWidth;
            }
	    */

        /*
            p {
            width: WidthVar + 50px;

         */
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new Declaration("width"))
                        .addChild(new AddOperation()
                                .addChild(new VariableReference("WidthVar"))
                                .addChild(new PixelLiteral("50px"))
                        )
                )
        );

        return new AST(stylesheet);
    }

    public static AST addAndSubSameType_false() {
        Stylesheet stylesheet = new Stylesheet();
        /*
            WidthVar := 50px;
			LinkColor := #ff0000;
			AdjustColor := TRUE;
			UseLinkColor := FALSE;
		 */
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("WidthVar"))
                .addChild(new PixelLiteral("50px"))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("LinkColor"))
                .addChild(new ColorLiteral("#ff0000"))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("AdjustColor"))
                .addChild(new BoolLiteral(true))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("UseLinkColor"))
                .addChild(new BoolLiteral(false))
        );

   	    /*
	        p {
	        background-color: #ffffff;
	        width: ParWidth;
            }
	    */

        /*
            p {
            width: WidthVar + 50%;

         */
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new Declaration("width"))
                        .addChild(new AddOperation()
                                .addChild(new VariableReference("WidthVar"))
                                .addChild(new PercentageLiteral("50%"))
                        )
                )
        );

        return new AST(stylesheet);
    }

    public static AST mulShouldUseAtLeastOneScalar_true() {
        Stylesheet stylesheet = new Stylesheet();
        /*
            WidthVar := 50px;

		 */
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("WidthVar"))
                .addChild(new PixelLiteral("50px"))
        );

   	    /*
	        p {
	        background-color: #ffffff;
	        width: ParWidth;
            }
	    */

        /*
            p {
            width: WidthVar * 50;

         */
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new Declaration("width"))
                        .addChild(new MultiplyOperation()
                                .addChild(new VariableReference("WidthVar"))
                                .addChild(new ScalarLiteral("50"))
                        )
                )
        );

        return new AST(stylesheet);
    }

    public static AST mulShouldUseAtLeastOneScalar_false() {
        Stylesheet stylesheet = new Stylesheet();
        /*
            WidthVar := 50px;

		 */
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("WidthVar"))
                .addChild(new PixelLiteral("50px"))
        );

   	    /*
	        p {
	        background-color: #ffffff;
	        width: ParWidth;
            }
	    */

        /*
            p {
            width: WidthVar * 50;

         */
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new Declaration("width"))
                        .addChild(new MultiplyOperation()
                                .addChild(new VariableReference("WidthVar"))
                                .addChild(new PixelLiteral("50px"))
                        )
                )
        );

        return new AST(stylesheet);
    }

    public static AST coloursShouldNotUsedInOperation_true() {
        Stylesheet stylesheet = new Stylesheet();
        /*
            p {
            width: 50 * 50px;

         */
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new Declaration("width"))
                        .addChild(new MultiplyOperation()
                                .addChild(new ScalarLiteral(50))
                                .addChild(new PixelLiteral("50px"))
                        )
                )
        );

        return new AST(stylesheet);
    }

    public static AST boolShouldNotUsedInOperation_true() {
        Stylesheet stylesheet = new Stylesheet();
        /*
            p {
            width: 50 * true;

         */
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new Declaration("width"))
                        .addChild(new MultiplyOperation()
                                .addChild(new ScalarLiteral(50))
                                .addChild(new BoolLiteral(true))
                        )
                )
        );

        return new AST(stylesheet);
    }

    public static AST ifClauseStatementsShouldUseBoolean_true() {
        Stylesheet stylesheet = new Stylesheet();
		/*
			AdjustColor := TRUE;
		 */

        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("AdjustColor"))
                .addChild(new BoolLiteral(true))
        );

   	    /*
	        p {
				if[AdjustColor] {
	    			color: #124532;
				}
			}
}
	    */
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new IfClause())
                        .addChild(new VariableReference("AdjustColor"))
                        .addChild((new Declaration("color")
                                .addChild(new ColorLiteral("#124532"))))
                )
        );
        return new AST(stylesheet);
    }

    public static AST ifClauseStatementsShouldUseBoolean_false() {
        Stylesheet stylesheet = new Stylesheet();
		/*
			AdjustColor := TRUE;
		 */

        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("AdjustColor"))
                .addChild(new BoolLiteral(true))
        );

   	    /*
	        p {
				if[AdjustColor] {
	    			color: #124532;
				}
			}
}
	    */
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new IfClause())
                        .addChild(new PixelLiteral(50))
                        .addChild((new Declaration("color")
                                .addChild(new ColorLiteral("#124532"))))
                )
        );
        return new AST(stylesheet);
    }


    public static AST operationsShouldBeAbleToHaveCascadingOperations() {
        Stylesheet stylesheet = new Stylesheet();
        /*
            WidthVar := 50px;

		 */
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("WidthVar"))
                .addChild(new PixelLiteral("50px"))
        );

        /*
            p {
            width: WidthVar + 50px * 5;

         */
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new Declaration("width"))
                        .addChild(new AddOperation()
                                .addChild(new VariableReference("WidthVar"))
                                .addChild(new MultiplyOperation()
                                        .addChild(new PixelLiteral(50))
                                        .addChild(new ScalarLiteral(5)))

                        )
                )
        );

        return new AST(stylesheet);
    }

    public static AST propertyShouldHaveLimitedNames() {
        Stylesheet stylesheet = new Stylesheet();
        /*
            WidthVar := 50px;

		 */
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("black"))
                .addChild(new PixelLiteral("000000"))
        );

        /*
        Controleer of bij declaraties het type van de value klopt met de property. Declaraties zoals width: #ff0000 of color: 12px zijn natuurlijk onzin.
            p {
            width: WidthVar + 50px;
            height: 50px
            color: #FFFFFF
            background-color: black

         */
        stylesheet.addChild((new Stylerule()
                .addChild(new TagSelector("p"))
                .addChild(new Declaration("width")
                        .addChild(new PixelLiteral(50)))
                .addChild(new Declaration("height")
                        .addChild(new PixelLiteral(50)))
                .addChild(new Declaration("color")
                        .addChild(new ColorLiteral("#FFFFFF")))
                .addChild(new Declaration("background-color")
                        .addChild(new VariableReference("black")))
                )
        );

        return new AST(stylesheet);
    }

    public static AST propertyShouldHaveLogicalValue() {
        Stylesheet stylesheet = new Stylesheet();
        /*
            WidthVar := 50px;

		 */
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("black"))
                .addChild(new PixelLiteral("000000"))
        );

        /*
        Controleer of bij declaraties het type van de value klopt met de property. Declaraties zoals width: #ff0000 of color: 12px zijn natuurlijk onzin.
            p {
            width: WidthVar + 50px;
            height: 50px
            color: #FFFFFF
            background-color: black

         */
        stylesheet.addChild((new Stylerule()
                        .addChild(new TagSelector("p"))
                        .addChild(new Declaration("width")
                                .addChild(new ColorLiteral("FFFFFF")))
                        .addChild(new Declaration("height")
                                .addChild(new PixelLiteral(50)))
                        .addChild(new Declaration("color")
                                .addChild(new ColorLiteral("#FFFFFF")))
                        .addChild(new Declaration("background-color")
                                .addChild(new VariableReference("black")))
                )
        );

        return new AST(stylesheet);
    }
}
