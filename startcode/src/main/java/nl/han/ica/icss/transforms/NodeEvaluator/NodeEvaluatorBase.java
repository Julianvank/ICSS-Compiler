package nl.han.ica.icss.transforms.NodeEvaluator;

import nl.han.ica.datastructures.SymbolTable;
import nl.han.ica.icss.ast.ASTNode;

public class NodeEvaluatorBase implements INodeEvaluator{
    protected SymbolTable table;


    public NodeEvaluatorBase(SymbolTable table){
        this.table = table;
    }

    public ASTNode evaluate(ASTNode node){
        System.out.println("reached: " + node.getNodeLabel());
        return node;
    }
}
