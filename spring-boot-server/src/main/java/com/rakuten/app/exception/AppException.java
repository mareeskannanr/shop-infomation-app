package com.rakuten.app.exception;

public class AppException extends RuntimeException {

    private String message;

    public AppException(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return message;
    }

}
