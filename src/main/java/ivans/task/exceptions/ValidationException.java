package ivans.task.exceptions;

public abstract class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
