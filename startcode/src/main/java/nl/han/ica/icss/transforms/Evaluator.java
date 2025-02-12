package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.datastructures.LinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.checker.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;

public class Evaluator implements Transform {

    private SymbolTable symbolTable;

    public Evaluator() {
        symbolTable = new SymbolTable();
    }

    @Override
    public void apply(AST ast) {
        ast.setRoot((Stylesheet) walkAst(ast.root));
    }

    private ASTNode walkAst(ASTNode node){
        //checkScope

//        node = evaluateNode(node);

        if(node instanceof VariableAssignment) {
            //check expression node and potentially change the Operation to a Literal.
            //Push var in symbolTable.
        }

        ArrayList<ASTNode> children = new ArrayList<>(node.getChildren());

        for (ASTNode cNode : children){
            node.removeChild(cNode);
            cNode = walkAst(cNode);
            node.addChild(cNode);
        }
    }

    public SymbolTable getSymbolTable(){
        return symbolTable;
    }
//
//    public ASTNode evaluateNode(ASTNode node){
//
//    }

}
