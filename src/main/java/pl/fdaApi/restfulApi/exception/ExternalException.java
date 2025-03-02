package pl.fdaApi.restfulApi.exception;

public class ExternalException extends RuntimeException {
    public ExternalException(String message) {
        super(message);
    }
}
