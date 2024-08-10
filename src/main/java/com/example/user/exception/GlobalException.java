package com.example.user.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = {ValidationException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(Exception e, WebRequest request) {
        ErrorResponse error = new ErrorResponse();

        error.setTimestamp(new Date());
        error.setStatus(HttpStatus.BAD_REQUEST.value());

        error.setPath(request.getDescription(false).replace("uri=", ""));
        String message = e.getMessage();

        if (e instanceof MethodArgumentNotValidException) {


            int start = message.lastIndexOf("[");
            int end = message.lastIndexOf("]");

            message = message.substring(start + 1, end - 1);
            error.setMessage(message);
            error.setError("Payload invalid");
        }

        else if (e instanceof ConstraintViolationException) {
            message = message.substring(message.indexOf(" ") +1);
            error.setMessage(message);
            error.setError("Parameter invalid");
        }

        return error;
    }
}
