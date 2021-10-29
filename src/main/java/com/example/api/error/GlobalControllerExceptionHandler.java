package com.example.api.error;

import java.util.List;
import java.util.ArrayList;
import org.springframework.validation.FieldError;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;


import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = { javax.validation.ConstraintViolationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse constraintViolationException(javax.validation.ConstraintViolationException ex) {
        return new ApiErrorResponse(400, 5, ex.getMessage());
    }

    @ExceptionHandler(value = { BadRequest.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse constraintViolationException(BadRequest ex) {
        return new ApiErrorResponse(400, 5, ex.getReason());
    }

    @ExceptionHandler(value = { MissingServletRequestParameterException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        return new ApiErrorResponse(400, 6, ex.getMessage());
    }

    // bad request (invalid request body)
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        System.out.println(ex.getBindingResult().getFieldErrors());
        List<String> errors = new ArrayList<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        // iterate over errors
        for (int i = 0; i < fieldErrors.size(); i++) {
            FieldError element = fieldErrors.get(i);
            String message = element.getDefaultMessage();
            // additional fields: element.getField(), element.getRejectedValue(), element.getObjectName()
            errors.add(message);
        }
        return new ApiErrorResponse(400, 7, "Validation error", errors);
    }
    
    // Not found
    @ExceptionHandler(value = {javax.persistence.EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ApiErrorResponse handleEntityNotFound(javax.persistence.EntityNotFoundException ex) {
        return new ApiErrorResponse(404, 0, ex.getMessage());
    }

    @ExceptionHandler(value = { NoHandlerFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse noHandlerFoundException(Exception ex) {
        return new ApiErrorResponse(404, 4041, ex.getMessage());
    }

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse unknownException(Exception ex) {
        return new ApiErrorResponse(500, 5002, ex.getMessage());
    }
}
