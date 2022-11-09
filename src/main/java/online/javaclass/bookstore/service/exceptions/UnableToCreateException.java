package online.javaclass.bookstore.service.exceptions;

public class UnableToCreateException extends AppException {
    public UnableToCreateException() {
    }

    public UnableToCreateException(String message) {
        super(message);
    }

    public UnableToCreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToCreateException(Throwable cause) {
        super(cause);
    }
}
