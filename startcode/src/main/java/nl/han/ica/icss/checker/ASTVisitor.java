package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.datastructures.StackNode;
import nl.han.ica.datastructures.UnderflowException;
import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.ast.ASTNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ASTVisitor {


    protected ASTNode current;
    protected ArrayList<ASTNode> children;
    protected int index = 0;

    public ASTVisitor(ASTNode current){
        this.children = current.getChildren();
        this.current = children.get(index);

    }

    public boolean isValid(){
        return index < children.size();
    }

    public void advance() {
        index++;
        if(isValid()) {
            current = children.get(index);
        }
    }

    public void advanceChild(){
        //keep track of childExamened


    }
    public ASTNode retrieve(){
        if(current == null){
            throw new NoSuchElementException();
        }
        return current;
    }



}
