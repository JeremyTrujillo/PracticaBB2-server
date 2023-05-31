package com.example.practicabitboxer2.conf;

import com.example.practicabitboxer2.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ItemEmptyException.class, ItemEmptyIdException.class, ItemEmptyCodeException.class,
            ItemEmptyDescriptionException.class, ItemEmptySuppliersException.class,
            ItemEmptyPriceReductionsException.class})
    public ResponseEntity<Object> handleItemBadRequestExceptions() {
        return new ResponseEntity<>("Missing attributes in item", BAD_REQUEST);
    }

    @ExceptionHandler(ItemInvalidIdException.class)
    public ResponseEntity<Object> handleItemInvalidIdException() {
        return new ResponseEntity<>("The id in parameter does not match the ID in item.", BAD_REQUEST);
    }

    @ExceptionHandler(ItemInvalidCodeException.class)
    public ResponseEntity<Object> handleItemInvalidCodeException() {
        return new ResponseEntity<>("The item code cannot be edited.", BAD_REQUEST);
    }

    @ExceptionHandler(ItemInvalidStateException.class)
    public ResponseEntity<Object> handleItemInvalidStateException() {
        return new ResponseEntity<>("A discontinued item cannot be edited.", BAD_REQUEST);
    }

    @ExceptionHandler(ItemSupplierDuplicatedException.class)
    public ResponseEntity<Object> handleItemSupplierDuplicatedException() {
        return new ResponseEntity<>("The new supplier is already associated with the item.", BAD_REQUEST);
    }

    @ExceptionHandler(OverlappingPriceReductionsException.class)
    public ResponseEntity<Object> handleOverlappingPriceReductionsException() {
        return new ResponseEntity<>("An overlapping between price reductions was found.", BAD_REQUEST);
    }

    @ExceptionHandler(ItemAlreadyDiscontinuedException.class)
    public ResponseEntity<Object> handleItemAlreadyDiscontinuedException() {
        return new ResponseEntity<>("The item is already discontinued.", BAD_REQUEST);
    }

    @ExceptionHandler({ItemDeactivatorEmptyException.class, ItemDeactivatorEmptyItemIdException.class,
            ItemDeactivatorEmptyUserIdException.class, ItemDeactivatorEmptyReasonException.class})
    public ResponseEntity<Object> handleItemDeactivatorEmptyException() {
        return new ResponseEntity<>("Missing attributes in item deactivator", BAD_REQUEST);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Object> handleItemNotFoundException() {
        return new ResponseEntity<>("Item not found", NOT_FOUND);
    }

    @ExceptionHandler(ItemCodeAlreadyExistsException.class)
    public ResponseEntity<Object> handleItemCodeAlreadyExistsException() {
        return new ResponseEntity<>("An item with the passed item code already exists.", CONFLICT);
    }

    @ExceptionHandler({UserEmptyException.class, UserEmptyUsernameException.class})
    public ResponseEntity<Object> handleUserBadRequestExceptions() {
        return new ResponseEntity<>("Missing attributes in user", BAD_REQUEST);
    }

    @ExceptionHandler(UserInvalidRoleException.class)
    public ResponseEntity<Object> handleUserRoleExceptions() {
        return new ResponseEntity<>("User role is invalid", BAD_REQUEST);
    }

    @ExceptionHandler(UserInvalidPasswordException.class)
    public ResponseEntity<Object> handleUserPasswordExceptions() {
        return new ResponseEntity<>("User password does not meet the requirements", BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException() {
        return new ResponseEntity<>("User not found", NOT_FOUND);
    }

    @ExceptionHandler(UserUsernameAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserUsernameAlreadyExistsException() {
        return new ResponseEntity<>("A user with the passed username already exists.", CONFLICT);
    }
}
