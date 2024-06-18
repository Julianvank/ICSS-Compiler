package nl.han.ica.datastructures;

public class ListNode<AnyType> {

    public AnyType element;
    public ListNode<AnyType> next;
    public ListNode(AnyType element) {
        this.element = element;
    }

    public ListNode(AnyType element, ListNode<AnyType> next){
        this.element = element;
        this.next = next;
    }

    public void setNext(ListNode<AnyType> next) {
        this.next = next;
    }

    public AnyType getValue() {
        return element;
    }


}
