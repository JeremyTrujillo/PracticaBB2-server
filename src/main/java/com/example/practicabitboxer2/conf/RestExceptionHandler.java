package com.example.practicabitboxer2.conf;

import com.example.practicabitboxer2.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ItemEmptyException.class, ItemEmptyCodeException.class, ItemEmptyDescriptionException.class})
    public ResponseEntity<Object> handleItemBadRequestExceptions() {
        return new ResponseEntity<>("Missing attributes in item", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ItemInvalidCodeException.class)
    public ResponseEntity<Object> handleItemInvalidCodeException() {
        return new ResponseEntity<>("The item code cannot be edited.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ItemInvalidStateException.class)
    public ResponseEntity<Object> handleItemInvalidStateException() {
        return new ResponseEntity<>("An inactive item cannot be edited.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Object> handleItemNotFoundException() {
        return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemCodeAlreadyExistsException.class)
    public ResponseEntity<Object> handleItemCodeAlreadyExistsException() {
        return new ResponseEntity<>("An item with the passed item code already exists.", HttpStatus.CONFLICT);
    }
}
