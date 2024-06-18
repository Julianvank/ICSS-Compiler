package nl.han.ica.datastructures;

import org.checkerframework.checker.units.qual.A;

public class LinkedList<AnyType> implements IHANLinkedList<AnyType> {

    public ListNode<AnyType> header;

    public LinkedList() {
        header = new ListNode<>(null);
    }

    @Override
    public void addFirst(AnyType value) {
        if(header == null) {
            this.header = new ListNode<>(value);
        } else {
            ListNode<AnyType> next = header;
            header = new ListNode<>(value, next);
        }
    }

    @Override
    public void clear() {
        header = null;
    }

    @Override
    public void insert(int index, AnyType value) {
        ListNode<AnyType> newNode = new ListNode<>(value);
        LinkedListIterator<AnyType> itr = findPrevious(index);

        if (index == 0) {
            newNode.setNext(itr.current);
            header = newNode;
            return;
        }

        //current.next = new ListNode( x, current.next ); potential change
        newNode.setNext(itr.current.next);
        itr.current.setNext(newNode);
    }

    @Override
    public void delete(int pos) {
        LinkedListIterator<AnyType> itr = findPrevious(pos);
        ListNode<AnyType> previous = itr.current;

        if(pos == 0){
            removeFirst();
            return;
        }

        itr.advance();

        previous.setNext(itr.current.next);
    }

    private LinkedListIterator<AnyType> findPrevious(int pos) {
        ListNode<AnyType> itr = header;
        int count = 0;

        while (itr.next != null &&
                count != pos - 1) {
            itr = itr.next;
            count++;
        }

        return new LinkedListIterator<AnyType>(itr);
    }

    @Override
    public AnyType get(int pos) {
        LinkedListIterator<AnyType> itr = new LinkedListIterator<>(header);

        for (int i = 0; i < pos; i++) {
            itr.advance();
        }

        return itr.retrieve();
    }

    @Override
    public void removeFirst() {
        if(header.next == null) return;
        header = header.next;
    }

    @Override
    public AnyType getFirst() {
        return header.getValue();
    }

    @Override
    public int getSize() {
        LinkedListIterator<AnyType> itr;
        int count = 0;

        for(itr = new LinkedListIterator<>(header); itr.isValid(); itr.advance()){
            count++;
        }

        return count;
    }

}
