package nl.han.ica.datastructures;

public class HANQueue<AnyType> implements IHANQueue<AnyType>{
    private AnyType[] theArray;
    private int currentSize;
    private int front;
    private int back;
    private static final int DEFAULT_CAPACITY = 10;

    public HANQueue(){
        theArray = (AnyType[]) new Object[DEFAULT_CAPACITY];
        clear();
    }

    @Override
    public void clear() {
        currentSize = 0;
        front = 0;
        back = -1;
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    @Override
    public void enqueue(Object value) {
        if(currentSize == theArray.length)
            doubleQueue();
        back = increment(back);
        theArray[back] = (AnyType) value;
        currentSize++;
    }

    @Override
    public AnyType dequeue() {
        if(isEmpty())
            throw new UnderflowException("ArrayQueue deque");
        currentSize--;

        AnyType returnValue = theArray[front];
        front = increment(front);
        return returnValue;
    }

    @Override
    public AnyType peek() {
        if(isEmpty())
            throw new UnderflowException("ArrayQueue peek");
        return theArray[front];
    }

    @Override
    public int getSize() {
        return 0;
    }

    private int increment(int x){
        if(++x == theArray.length)
            x = 0;
        return x;
    }

    private void doubleQueue(){
        AnyType [] newArray;

        newArray = (AnyType[]) new Object[theArray.length * 2];

        for(int i = 0; i < currentSize; i++)
            newArray[i] = theArray[front];

        theArray = newArray;
        front = 0;
        back = currentSize-1;
    }
}
