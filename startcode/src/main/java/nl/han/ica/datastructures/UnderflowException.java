package nl.han.ica.datastructures;

public class UnderflowException extends RuntimeException {
    public UnderflowException(String arrayQueueDequeue) {
        super(arrayQueueDequeue);
    }
}