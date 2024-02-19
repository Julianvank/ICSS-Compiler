package nl.han.ica.icss.parser;


import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.datastructures.UnderflowException;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {

    //Accumulator attributes:
    private AST ast;
    private Set<ParserRuleContext> visitors;

    //Use this to keep track of the parent nodes when recursively traversing the ast
    private HANStack<ASTNode> currentContainer;

    public ASTListener() {
        ast = new AST();
        currentContainer = new HANStack<>();
        visitors = new HashSet<>();

//        currentContainer.push(ast);
    }

    public AST getAST() {
        return ast;
    }

    @Override
    public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
        enter(ast.root, ctx);
    }

    @Override
    public void enterStylerule(ICSSParser.StyleruleContext ctx) {
        enter(new Stylerule(), ctx);
    }

    @Override
    public void enterVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
        enter(new VariableAssignment(), ctx);
    }

    @Override
    public void enterProperty(ICSSParser.PropertyContext ctx) {
        enter(new PropertyName(ctx.getText()), ctx);
    }

    @Override
    public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
        enter(new Declaration(), ctx);
    }


    @Override
    public void enterVariableReference(ICSSParser.VariableReferenceContext ctx) {
        enter(new VariableReference(ctx.getText()), ctx);
    }

    /*
     * Selector Expressions
     */
    @Override
    public void enterIdSelector(ICSSParser.IdSelectorContext ctx) {
        enter(new IdSelector(ctx.getText()), ctx);
    }

    @Override
    public void enterClassSelector(ICSSParser.ClassSelectorContext ctx) {
        enter(new ClassSelector(ctx.getText()), ctx);
    }

    @Override
    public void enterTagSelector(ICSSParser.TagSelectorContext ctx) {
        enter(new TagSelector(ctx.getText()), ctx);
    }

    @Override
    public void enterMultiplyOperation(ICSSParser.MultiplyOperationContext ctx) {
        enter(new MultiplyOperation(), ctx);
    }

    @Override
    public void enterAddOperation(ICSSParser.AddOperationContext ctx) {
        enter(new AddOperation(), ctx);
    }

    @Override
    public void enterSubtractOperation(ICSSParser.SubtractOperationContext ctx) {
        enter(new SubtractOperation(), ctx);
    }

    /*
     * Literal Expressions
     */
    @Override
    public void enterColorLiteral(ICSSParser.ColorLiteralContext ctx) {
        enter(new ColorLiteral(ctx.getText()), ctx);
    }

    @Override
    public void enterPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
        enter(new PixelLiteral(ctx.getText()), ctx);
    }

    @Override
    public void enterPercentageLiteral(ICSSParser.PercentageLiteralContext ctx) {
        enter(new PercentageLiteral(ctx.getText()), ctx);
    }

    @Override
    public void enterBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
        enter(new BoolLiteral(ctx.getText()), ctx);
    }

    @Override
    public void enterScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
        enter(new ScalarLiteral(ctx.getText()), ctx);
    }

    /*
     * Operation Expressions
     */


    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        if (!visitors.contains(ctx)) return;
        try {
                visitors.remove(ctx);

                currentContainer.pop();
        } catch (UnderflowException e) {
            throw new RuntimeException(e);
        }
    }

    private void enter(ASTNode node, ParserRuleContext ctx) {
        if (!currentContainer.isEmpty()) {
            try {
                currentContainer.peek().addChild(node);
//                System.out.println(currentContainer.peek());
            } catch (UnderflowException e) {
                throw new RuntimeException(e);
            }
        }

        visitors.add(ctx);
        currentContainer.push(node);
    }
}