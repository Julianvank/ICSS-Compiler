package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.SymbolTable;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.checker.NodeCheckerFactory;
import nl.han.ica.icss.transforms.NodeEvaluator.INodeEvaluator;

import java.util.ArrayList;
import java.util.List;

public class Evaluator implements Transform {

    private NodeCheckerFactory nodeCheckerFactory;
    private static final boolean PUSH_SCOPE = true;
    private static final boolean POP_SCOPE = false;
    private SymbolTable symbolTable;

    private NodeEvaluatorFactory nodeEvaluatorFactory;

    @Override
    public void apply(AST ast) {
        this.symbolTable = ast.getSymbolTable();
        this.nodeEvaluatorFactory = new NodeEvaluatorFactory();

        ast.root = (Stylesheet) traverseAst(ast.root);
    }

    private ASTNode traverseAst(ASTNode node){
        /*
            Walk through the AST
            1. check for scope
                a) pushScope
                b) do nothing
            2. if(instanceOfExpression)
                1) if(variableAssignment)
                    a) getAssignment
                2) if(instanceof literal)
                    a) return
                3) if(instanceof operation)
                    a) calculateValue
            3. if(instanceOf ifClause)
                a) if(conditional == true)
                    a) replace if with body
                    b) if(exists)
                        a) replace with elseclause
            4. get children from node and walk.

            5. check for scope
                a) popScope
         */

        modifyScope(node, PUSH_SCOPE);

        INodeEvaluator nodeEvaluator;
        nodeEvaluator = nodeEvaluatorFactory.createNodeEvaluator(this, node);

        node = nodeEvaluator.evaluate(node);

        if(node.getChildren().isEmpty()){ return node;}

        List<ASTNode> children = new ArrayList<>(node.getChildren());

        for (ASTNode child : children){
            node.removeChild(child);
            node.addChild(traverseAst(child));
        }

//        for(ASTNode child : node.getChildren()){
//            node.addChild(node.removeChild(traverseAst(child)));
//        }

        modifyScope(node, POP_SCOPE);

        return node;
    }

    private void modifyScope(ASTNode node, boolean pushOrPop) {
        if (NodeCheckerFactory.shouldScopeBePushed(node)) return;

        if(pushOrPop == PUSH_SCOPE){
            symbolTable.findScope(node);
        } else if (pushOrPop == POP_SCOPE) {
            symbolTable.popScope();
        }
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }
}
