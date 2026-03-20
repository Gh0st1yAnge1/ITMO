package org.example.exception;

public class InputCancelledException extends RuntimeException {
    public InputCancelledException(String message) {
        super(message);
    }
}
