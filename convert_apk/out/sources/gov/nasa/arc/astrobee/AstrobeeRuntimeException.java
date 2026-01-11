package gov.nasa.arc.astrobee;

public class AstrobeeRuntimeException extends RuntimeException {
    public AstrobeeRuntimeException() {
    }

    public AstrobeeRuntimeException(String message) {
        super(message);
    }

    public AstrobeeRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AstrobeeRuntimeException(Throwable cause) {
        super(cause);
    }
}
