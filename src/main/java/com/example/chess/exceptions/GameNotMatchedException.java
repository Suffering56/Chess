package com.example.chess.exceptions;

public class GameNotMatchedException extends Exception {

    public GameNotMatchedException() {
    }

    public GameNotMatchedException(String message) {
        super(message);
    }

    public GameNotMatchedException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameNotMatchedException(Throwable cause) {
        super(cause);
    }

    public GameNotMatchedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
