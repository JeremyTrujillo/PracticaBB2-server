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

    @ExceptionHandler(ItemSupplierDuplicatedException.class)
    public ResponseEntity<Object> handleItemSupplierDuplicatedException() {
        return new ResponseEntity<>("The new supplier is already associated with the item.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ItemAlreadyInactiveException.class)
    public ResponseEntity<Object> handleItemAlreadyInactiveException() {
        return new ResponseEntity<>("The item is already inactive.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Object> handleItemNotFoundException() {
        return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemCodeAlreadyExistsException.class)
    public ResponseEntity<Object> handleItemCodeAlreadyExistsException() {
        return new ResponseEntity<>("An item with the passed item code already exists.", HttpStatus.CONFLICT);
    }

    @ExceptionHandler({UserEmptyException.class, UserEmptyUsernameException.class})
    public ResponseEntity<Object> handleUserBadRequestExceptions() {
        return new ResponseEntity<>("Missing attributes in user", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserInvalidRoleException.class)
    public ResponseEntity<Object> handleUserRoleExceptions() {
        return new ResponseEntity<>("User role is invalid", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserInvalidPasswordException.class)
    public ResponseEntity<Object> handleUserPasswordExceptions() {
        return new ResponseEntity<>("User password does not meet the requirements", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException() {
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserUsernameAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserUsernameAlreadyExistsException() {
        return new ResponseEntity<>("A user with the passed username already exists.", HttpStatus.CONFLICT);
    }
}
