package Exceptions;

public class NotFoundEntryException extends Exception {

    public NotFoundEntryException() {
        super();
    }

    public NotFoundEntryException(String message) {
        super(message);
    }

    public NotFoundEntryException(String message, Throwable cause) {
        super(message, cause);
    }

}