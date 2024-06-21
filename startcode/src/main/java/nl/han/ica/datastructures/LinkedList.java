package nl.han.ica.datastructures;


public class LinkedList<AnyType> implements IHANLinkedList<AnyType> {

    private ListNode<AnyType> header;

    public LinkedList() {
        header = new ListNode<AnyType>(null);
    }

    /**
     * Adds value to the front of the list
     *
     * @param value generic value to be added
     */
    @Override
    public void addFirst(AnyType value) {
        header = new ListNode<>(value, header);
    }

    public boolean isEmpty(){
        return header.next == null;
    }

    /**
     * Clears list. Size equals 0 afterwards
     */
    @Override
    public void clear() {
        header.next = null;
    }

    /**
     * Adds value to index position
     *
     * @param index the position
     * @param value the value to add at index
     */
    @Override
    public void insert(int index, AnyType value) {
        LinkedListIterator<AnyType> itr = zeroth();
        for(int i = 0; i < index; i++){
            itr.advance();
        }
        itr.current.next = new ListNode<>(value, itr.current.next);
    }

    /**
     * Deletes value at position
     *
     * @param pos position where value is deleted
     */
    @Override
    public void delete(int pos) {
        LinkedListIterator<AnyType> itr = zeroth();
        for(int i = 0; i < pos; i++){
            itr.advance();
        }
        itr.current.next = itr.current.next.next;
    }

    /**
     * Returns generic value T at postion
     *
     * @param pos position to look up value
     * @return value at position pos
     */
    @Override
    public AnyType get(int pos) {
        LinkedListIterator<AnyType> itr = zeroth();
        for(int i = 0; i <= pos; i++){
            itr.advance();
        }
        return itr.isValid() ? itr.current.element : null;
    }

    /**
     * Removes first element
     */
    @Override
    public void removeFirst() {
        header = header.next;
    }

    /**
     * Returns first element in O(n) time
     *
     * @return first element
     */
    @Override
    public AnyType getFirst() {
        return header.element;
    }

    /**
     * Determines size of the list, equals the number of stored items but not the header node
     *
     * @return number of items in list
     */
    @Override
    public int getSize() {
        LinkedListIterator<AnyType> itr = first();
        int size = 0;
        while(itr.isValid()){
            size++;
            itr.advance();
        }
        return size;
    }

    public static <AnyType> void printList(LinkedList<AnyType> theList){
        if(theList.isEmpty()){
            System.out.print("List is empty.");
        }else{
            LinkedListIterator<AnyType> itr = theList.first();
            for(; itr.isValid(); itr.advance()){
                System.out.print(itr.retrieve() + " ");
            }
        }
        System.out.println();
    }





    public LinkedListIterator<AnyType> zeroth(){
        return new LinkedListIterator<AnyType>(header);
    }

    public LinkedListIterator<AnyType> first(){
        return new LinkedListIterator<AnyType> (header.next);
    }

    static class ListNode<AnyType> {

        public AnyType element;
        public ListNode<AnyType> next;

        public ListNode(AnyType element) {
            this.element = element;
        }

        public ListNode(AnyType element, ListNode<AnyType> next){
            this.element = element;
            this.next = next;
        }

    }
}
