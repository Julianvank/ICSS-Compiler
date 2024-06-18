package nl.han.ica.datastructures;

public class StackNode<AnyType> {

    public AnyType element;
    public int timesPopped = 0;

    public StackNode(AnyType element){
        this.element = element;
    }
}
