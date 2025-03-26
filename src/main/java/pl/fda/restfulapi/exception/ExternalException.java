package pl.fda.restfulapi.exception;

public class ExternalException extends RuntimeException {
    public ExternalException(String message) {
        super(message);
    }
}
