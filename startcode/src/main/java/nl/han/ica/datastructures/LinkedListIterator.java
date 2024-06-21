package nl.han.ica.datastructures;

public class LinkedListIterator<AnyType> {

    LinkedList.ListNode<AnyType> current;

    LinkedListIterator(LinkedList.ListNode<AnyType> theNode){
        current = theNode;
    }

    public boolean isValid(){
        return current != null;
    }

    public AnyType retrieve(){
        return isValid() ? current.element : null;
    }

    public void advance(){
        if(isValid()){
            current = current.next;
        }
    }

}
