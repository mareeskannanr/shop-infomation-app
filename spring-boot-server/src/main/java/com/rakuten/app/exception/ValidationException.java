package com.rakuten.app.exception;

import java.util.List;

public class ValidationException extends RuntimeException {

    private List<String> errorList;

    public ValidationException(List<String> errorList) {
        this.errorList = errorList;
    }

    public List<String> getErrorList() {
        return errorList;
    }
}
