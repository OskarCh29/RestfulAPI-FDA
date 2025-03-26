package pl.fdaApi.restfulapi.exception;

public class ExternalException extends RuntimeException {
    public ExternalException(String message) {
        super(message);
    }
}
