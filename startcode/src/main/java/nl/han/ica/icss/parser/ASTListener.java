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

import javax.swing.text.Style;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {

    //Accumulator attributes:
    private final AST ast;
    private final Set<ParserRuleContext> visitors;

    //Use this to keep track of the parent nodes when recursively traversing the ast
    private final HANStack<ASTNode> currentContainer;

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
        ASTNode node = new Stylesheet();
        pushStack(node);
//        pushStack(ast.root);
    }

    @Override
    public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
        try {
            ast.setRoot((Stylesheet) currentContainer.pop());
        } catch (UnderflowException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void enterStylerule(ICSSParser.StyleruleContext ctx) {
        ASTNode node = new Stylerule();
        pushStack(node);
    }

    @Override
    public void exitStylerule(ICSSParser.StyleruleContext ctx) {
        ASTNode variableAssignment;
        try {
            variableAssignment = currentContainer.pop();
            currentContainer.peek().addChild(variableAssignment);
        } catch (UnderflowException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
        ASTNode node = new Declaration();
        pushStack(node);
    }

    @Override
    public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
        ASTNode node;
        try {
            node = currentContainer.pop();
            currentContainer.peek().addChild(node);
        } catch (UnderflowException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void enterVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
        ASTNode node = new VariableAssignment();
        pushStack(node);
    }

    @Override
    public void exitVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
        ASTNode variableAssignment;
        try {
            variableAssignment = currentContainer.pop();
            currentContainer.peek().addChild(variableAssignment);
        } catch (UnderflowException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void enterVariableReference(ICSSParser.VariableReferenceContext ctx) {
        ASTNode node = new VariableReference(ctx.getText());
        try {
            currentContainer.peek().addChild(node);
        } catch (UnderflowException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public void exitVariableReference(ICSSParser.VariableReferenceContext ctx) {
//        ASTNode variableAssignment;
//        try {
//            variableAssignment = currentContainer.pop();
//            currentContainer.peek().addChild(variableAssignment);
//        } catch (UnderflowException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void enterProperty(ICSSParser.PropertyContext ctx) {
        ASTNode node = new PropertyName(ctx.getText());
        try {
            currentContainer.peek().addChild(node);
        } catch (UnderflowException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public void exitProperty(ICSSParser.PropertyContext ctx) {
//        ASTNode variableAssignment;
//        try {
//            variableAssignment = currentContainer.pop();
//            currentContainer.peek().addChild(variableAssignment);
//        } catch (UnderflowException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void enterIdSelector(ICSSParser.IdSelectorContext ctx) {
        ASTNode node = new IdSelector(ctx.getText());
        pushStack(node);
    }

    @Override
    public void exitIdSelector(ICSSParser.IdSelectorContext ctx) {
        ASTNode variableAssignment;
        try {
            variableAssignment = currentContainer.pop();
            currentContainer.peek().addChild(variableAssignment);
        } catch (UnderflowException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void enterClassSelector(ICSSParser.ClassSelectorContext ctx) {
        ASTNode node = new ClassSelector(ctx.getText());
        pushStack(node);
    }

    @Override
    public void exitClassSelector(ICSSParser.ClassSelectorContext ctx) {
        ASTNode variableAssignment;
        try {
            variableAssignment = currentContainer.pop();
            currentContainer.peek().addChild(variableAssignment);
        } catch (UnderflowException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void enterTagSelector(ICSSParser.TagSelectorContext ctx) {
        ASTNode node = new TagSelector(ctx.getText());
        pushStack(node);
    }

    @Override
    public void exitTagSelector(ICSSParser.TagSelectorContext ctx) {
        ASTNode variableAssignment;
        try {
            variableAssignment = currentContainer.pop();
            currentContainer.peek().addChild(variableAssignment);
        } catch (UnderflowException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void enterColorLiteral(ICSSParser.ColorLiteralContext ctx) {
        ASTNode node = new ColorLiteral(ctx.getText());
        try {
            currentContainer.peek().addChild(node);
        } catch (UnderflowException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public void exitColorLiteral(ICSSParser.ColorLiteralContext ctx) {
//        ASTNode variableAssignment;
//        try {
//            variableAssignment = currentContainer.pop();
//            currentContainer.peek().addChild(variableAssignment);
//        } catch (UnderflowException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void enterPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
        ASTNode node = new PixelLiteral(ctx.getText());
        try {
            currentContainer.peek().addChild(node);
        } catch (UnderflowException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public void exitPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
//        ASTNode variableAssignment;
//        try {
//            variableAssignment = currentContainer.pop();
//            currentContainer.peek().addChild(variableAssignment);
//        } catch (UnderflowException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void enterPercentageLiteral(ICSSParser.PercentageLiteralContext ctx) {
        ASTNode node = new PercentageLiteral(ctx.getText());
        try {
            currentContainer.peek().addChild(node);
        } catch (UnderflowException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public void exitPercentageLiteral(ICSSParser.PercentageLiteralContext ctx) {
//        ASTNode variableAssignment;
//        try {
//            variableAssignment = currentContainer.pop();
//            currentContainer.peek().addChild(variableAssignment);
//        } catch (UnderflowException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void enterBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
        ASTNode node = new BoolLiteral(ctx.getText());
        try {
            currentContainer.peek().addChild(node);
        } catch (UnderflowException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public void exitBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
//        ASTNode variableAssignment;
//        try {
//            variableAssignment = currentContainer.pop();
//            currentContainer.peek().addChild(variableAssignment);
//        } catch (UnderflowException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void enterScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
        ASTNode node = new ScalarLiteral(ctx.getText());
        try {
            currentContainer.peek().addChild(node);
        } catch (UnderflowException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public void exitScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
//        ASTNode variableAssignment;
//        try {
//            variableAssignment = currentContainer.pop();
//            currentContainer.peek().addChild(variableAssignment);
//        } catch (UnderflowException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void enterExpression(ICSSParser.ExpressionContext ctx) {
        if (ctx.getChildCount() == 3) {
            Operation operation;
            switch (ctx.getChild(1).getText()) {
                case "*":
                    operation = new MultiplyOperation();
                    break;
                case "+":
                    operation = new AddOperation();
                    break;
                default:
                    operation = new SubtractOperation();
            }
            currentContainer.push(operation);
        }
    }

    @Override
    public void exitExpression(ICSSParser.ExpressionContext ctx) {
        if (expressionHasTerminalNode(ctx)) {
            ASTNode variableAssignment;
            try {
                variableAssignment = currentContainer.pop();
                currentContainer.peek().addChild(variableAssignment);
            } catch (UnderflowException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void enterIfClause(ICSSParser.IfClauseContext ctx) {
        ASTNode node = new IfClause();
        pushStack(node);
    }

    @Override
    public void exitIfClause(ICSSParser.IfClauseContext ctx) {
        ASTNode node;
        try {
            node = currentContainer.pop();
            currentContainer.peek().addChild(node);
        } catch (UnderflowException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void enterElseClause(ICSSParser.ElseClauseContext ctx) {
        ASTNode node = new ElseClause();
        pushStack(node);
    }

    @Override
    public void exitElseClause(ICSSParser.ElseClauseContext ctx) {
        ASTNode node;
        try {
            node = currentContainer.pop();
            currentContainer.peek().addChild(node);
        } catch (UnderflowException e) {
            throw new RuntimeException(e);
        }
    }

    private void pushStack(ASTNode node) {
        currentContainer.push(node);
    }

    private boolean expressionHasTerminalNode(ICSSParser.ExpressionContext ctx) {
        return ctx.MIN() != null || ctx.PLUS() != null || ctx.MUL() != null;
    }
}