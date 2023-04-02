package online.javaclass.bookstore.service.exceptions;

public class UnableToFindException extends AppException {
    public UnableToFindException() {
    }

    public UnableToFindException(String message) {
        super(message);
    }

    public UnableToFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToFindException(Throwable cause) {
        super(cause);
    }
}
