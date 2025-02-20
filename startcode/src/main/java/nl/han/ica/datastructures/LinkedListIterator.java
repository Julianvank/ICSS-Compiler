package nl.han.ica.datastructures;

public class LinkedListIterator<AnyType> {

    HANLinkedList.ListNode<AnyType> current;

    LinkedListIterator(HANLinkedList.ListNode<AnyType> theNode){
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
