package com.rakuten.app.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rakuten.app.exception.AppException;
import com.rakuten.app.exception.ValidationException;
import com.rakuten.app.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity handleException(AppException exception) {
        ObjectNode error = new ObjectMapper().createObjectNode();
        error.put("timestamp", LocalDateTime.now().toString());
        error.put("error", exception.getErrorMessage());

        return ResponseEntity.unprocessableEntity().body(error);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handleException(ValidationException exception) {
        return ResponseEntity.badRequest().body(exception.getErrorList());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(AppConstants.INTERNAL_SERVER_ERR_MSG);
    }

}
