package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.datastructures.LinkedList;
import nl.han.ica.icss.ast.*;

import java.util.HashMap;

public class Evaluator implements Transform {

    private IHANLinkedList<HashMap<String, Literal>> symbolTable;

    public void pushScope() {
        symbolTable.addFirst(new HashMap<>());
    }

    public void popScope() {
        symbolTable.removeFirst();
    }

    public Evaluator() {
        symbolTable = new LinkedList<>();
    }

    @Override
    public void apply(AST ast) {
        System.out.println(ast.getSymbolTable().toString());;
//        traverseAst(ast.root);
    }

    private void traverseAst(ASTNode node){
        pushScope();

        if(node == null){
            return;
        }
        for (ASTNode child : node.getChildren()) {

            //TODO write if for instance of push scope
            if(child instanceof Stylerule){
                pushScope();
            }
            if(child instanceof IfClause){
                pushScope();
            }
            checkType(child);

            traverseAst(child);
        }
    }

    private void checkType(ASTNode node) {
        if (node == null) {
            return;
        }

        if (node instanceof IfClause){
            node = EvaluateAndTransformIfClause(node);
            traverseAst(node);
        }
        if (node instanceof Expression){
            node = EvaluateAndTransformExpression(node);
        }
    }

    private ASTNode EvaluateAndTransformExpression(ASTNode node) {
        //TODO add variable to symbol table;
        System.out.println(node.getNodeLabel());
        return node;
    }

    private ASTNode EvaluateAndTransformIfClause(ASTNode node) {
        System.out.println(node.getNodeLabel() + " " + ((IfClause) node).conditionalExpression);

        return node;
    }
}
