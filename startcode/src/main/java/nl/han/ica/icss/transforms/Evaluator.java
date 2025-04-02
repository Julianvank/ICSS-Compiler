package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.datastructures.SymbolTable;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.checker.NodeCheckerFactory;
import nl.han.ica.icss.transforms.NodeEvaluator.ExpressionEvaluator;
import nl.han.ica.icss.transforms.NodeEvaluator.INodeEvaluator;
import nl.han.ica.icss.transforms.NodeEvaluator.IfClauseEvaluator;

import java.util.ArrayList;
import java.util.List;

public class Evaluator implements Transform {

    private NodeCheckerFactory nodeCheckerFactory;
    private static final boolean PUSH_SCOPE = true;
    private static final boolean POP_SCOPE = false;
    private SymbolTable symbolTable;
    private IHANStack<ASTNode> depthPath;

    private NodeEvaluatorFactory nodeEvaluatorFactory;

    @Override
    public void apply(AST ast) {
        this.symbolTable = ast.getSymbolTable();
        this.nodeEvaluatorFactory = new NodeEvaluatorFactory();
        this.depthPath = new HANStack<>();

        ast.root = (Stylesheet) traverseAst(ast.root);
    }

    private ASTNode traverseAst(ASTNode node){
        depthPath.push(node);

        ArrayList<ASTNode> children = new ArrayList<>(node.getChildren());

        for(ASTNode child : children){
            traverseAst(child);

            if(child instanceof IfClause){
                IfClauseEvaluator eval = new IfClauseEvaluator(symbolTable);
                IfClause tempNode = (IfClause) eval.evaluate(child);

                node.removeChild(child);

                for(ASTNode child2 : tempNode.body){
                    node.addChild(child2);
                }
            }
        }

//        System.out.println("==========");
//        System.out.println(depthPath.toString());

//        if(node instanceof IfClause){
//            IfClauseEvaluator evaluator = new IfClauseEvaluator(symbolTable);
//            node = evaluator.evaluate(node);
//            depthPath.pop();
//            ASTNode parentNode = depthPath.peek();
//            for(ASTNode body : node.getChildren()){
//                parentNode.addChild(body);
//            }
//            depthPath.push(node);
//            parentNode.removeChild(node);
//        }

        if(node instanceof Expression){
            ExpressionEvaluator evaluator = new ExpressionEvaluator(symbolTable);
            node = evaluator.evaluate(node);
        }

        depthPath.pop();
        return node;
    }

    private void modifyScope(ASTNode node, boolean pushOrPop) {
        if (NodeCheckerFactory.shouldScopeBePushed(node)) return;

        if(pushOrPop == PUSH_SCOPE){
            symbolTable.findScope(node);
            depthPath.push(node);
        } else if (pushOrPop == POP_SCOPE) {
            symbolTable.popScope();
            depthPath.pop();
        }
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }
}
