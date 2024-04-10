package nl.han.ica.datastructures;

public class UnderflowException extends Throwable {
    public UnderflowException(String arrayQueueDequeue) {
        super(arrayQueueDequeue);
    }
}