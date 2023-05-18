package com.example.practicabitboxer2.conf;

import com.example.practicabitboxer2.exceptions.ItemEmptyException;
import com.example.practicabitboxer2.exceptions.ItemInvalidCodeException;
import com.example.practicabitboxer2.exceptions.ItemInvalidDescriptionException;
import com.example.practicabitboxer2.exceptions.ItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ItemEmptyException.class, ItemInvalidCodeException.class, ItemInvalidDescriptionException.class})
    public ResponseEntity<Object> handleItemBadRequestExceptions() {
        return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Object> handleItemNotFoundException() {
        return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
    }
}
