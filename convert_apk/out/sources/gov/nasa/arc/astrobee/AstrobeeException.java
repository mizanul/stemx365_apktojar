package gov.nasa.arc.astrobee;

public class AstrobeeException extends Exception {
    public AstrobeeException() {
    }

    public AstrobeeException(Throwable cause) {
        super(cause);
    }

    public AstrobeeException(String message) {
        super(message);
    }

    public AstrobeeException(String message, Throwable cause) {
        super(message, cause);
    }
}
