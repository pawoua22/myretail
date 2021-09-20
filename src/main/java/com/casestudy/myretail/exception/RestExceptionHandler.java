package com.casestudy.myretail.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.validation.ValidationException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<Object> handleNotFound(Exception ex) {
        ApiResponseError error = new ApiResponseError(
                HttpStatus.NOT_FOUND,
                ex.getLocalizedMessage(),
                "Resource not found");

        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
    }

    @ExceptionHandler({ ValidationException.class})
    public ResponseEntity<Object> handleValidation(Exception ex) {
        ApiResponseError error = new ApiResponseError(
                HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(),
                "Error occurred");

        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
    }

    @ExceptionHandler({ Exception.class, RestTemplateException.class })
    public ResponseEntity<Object> handleAll(Exception ex) {
        ApiResponseError error = new ApiResponseError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getLocalizedMessage(),
                "Error occurred");

        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
    }
}
