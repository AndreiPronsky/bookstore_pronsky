package online.javaclass.bookstore.exceptions;

public class UnableToDeleteException extends AppException {
    public UnableToDeleteException() {
    }

    public UnableToDeleteException(String message) {
        super(message);
    }

    public UnableToDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToDeleteException(Throwable cause) {
        super(cause);
    }
}
