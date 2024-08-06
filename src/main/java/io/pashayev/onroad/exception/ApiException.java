package io.pashayev.onroad.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
