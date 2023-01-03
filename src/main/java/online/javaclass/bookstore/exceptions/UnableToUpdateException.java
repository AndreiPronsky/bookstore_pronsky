package online.javaclass.bookstore.exceptions;

public class UnableToUpdateException extends AppException {
    public UnableToUpdateException() {
    }

    public UnableToUpdateException(String message) {
        super(message);
    }

    public UnableToUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToUpdateException(Throwable cause) {
        super(cause);
    }
}
