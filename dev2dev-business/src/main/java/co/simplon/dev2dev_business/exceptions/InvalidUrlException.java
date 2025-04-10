package co.simplon.dev2dev_business.exceptions;

public class InvalidUrlException extends RuntimeException {
    public InvalidUrlException(String message, Throwable cause) {
        super(message, cause);
    }
}
