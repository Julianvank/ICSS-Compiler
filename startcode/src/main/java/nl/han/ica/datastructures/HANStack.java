package nl.han.ica.datastructures;

import nl.han.ica.icss.ast.ASTNode;

public class HANStack<AnyType> implements IHANStack{
    private AnyType [] theArray;
    private int topOfStack; //array Index of top elements
    private static final int DEFAULT_CAPACITY = 10;

    public HANStack(){
        theArray = (AnyType []) new Object[DEFAULT_CAPACITY];
        topOfStack = -1; // -1 == empty
    }
    public boolean isEmpty(){
        return topOfStack == -1;
    }
    public void makeEmpty(){
        topOfStack = -1;
    }

    @Override
    public void push(Object value) {
        if(topOfStack + 1 == theArray.length){
            doubleArray(theArray);
        }
         theArray[++topOfStack] = (AnyType) value;
    }

    @Override
    public AnyType pop() throws UnderflowException {
        if(isEmpty()){
            throw new UnderflowException("ArrayStack topAndPop");
        }
        return theArray[topOfStack--];
    }

    @Override
    public AnyType peek() throws UnderflowException {
        if(isEmpty()){
            throw new UnderflowException("ArrayStack top");
        }
        return theArray[topOfStack];
    }

    private void doubleArray(AnyType[] array){
        AnyType[] tmp = (AnyType[]) new Object[2* array.length];
        for (int i = 0; i < array.length; i++) {
            tmp[i] = array[i];
        }
        this.theArray = tmp;
    }

}