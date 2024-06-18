//package nl.han.ica.icss.checker;
//
//import nl.han.ica.datastructures.ListNode;
//import nl.han.ica.icss.ast.ASTNode;
//
//import java.util.NoSuchElementException;
//
//public class ASTWalker <AnyType> {
//
//    protected AnyType theTree;
//    protected ListWalkerNode<AnyType> current;
//    ListWalkerNode<AnyType> firstChild;
//    ListWalkerNode<AnyType> nextSibling;
//
//    public ASTWalker(AnyType astNode){
//        theTree = astNode; current = null;
//    }
//
//    public boolean isValid(){
//        return current != null;
//    }
//
//    public void first(){
//
//    }
//    public void advance(){
//
//    }
//
//    final public AnyType retrieve(){
//        if(current == null){throw new NoSuchElementException();}
//        return current.getElement();
//    }
//
//    public static class ListWalkerNode <AnyType> {
//        AnyType node;
//        int timesPopped = 0;
//
//        public ListWalkerNode(AnyType node){
//            this.node = node;
//        }
//    }
//}
