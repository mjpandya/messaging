package io.tntra.service.messaging.exceptions;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

@ControllerAdvice
public class MessageExceptionalHandler {

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<CustomMessageException> noMessageFoundExceptionHandler(WebRequest webRequest, NoSuchElementException ex){
        CustomMessageException exception = new CustomMessageException("1001", ex.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<CustomMessageException> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
        CustomMessageException exception=null;
        if(!ex.getFieldError().getField().isEmpty()) {
            exception = new CustomMessageException("1002", "Missing/Error with Field : [" + ex.getFieldError().getField() +"]");
        }
        else {
            exception = new CustomMessageException("1002", "Missing/Error Mandatory Field in Request");
        }
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }
}
