package com.example.practicabitboxer2.controller;

import com.example.practicabitboxer2.dtos.UserDTO;
import com.example.practicabitboxer2.exceptions.*;
import com.example.practicabitboxer2.services.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final @NonNull UserService service;

    @PostMapping(value = "/login")
    public ResponseEntity<Void> login(@RequestBody UserDTO user) {
        service.login();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserDTO user) {
        checkUserConstraints(user);
        UserDTO userByUsername = service.findByUsername(user.getUsername());
        if (userByUsername != null) {
            throw new UserUsernameAlreadyExistsException();
        }
        service.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByUsername(@RequestParam String username) {
        UserDTO user = service.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        service.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void checkUserConstraints(UserDTO user) {
        if (user == null) {
            throw new UserEmptyException();
        }
        if (user.getUsername() == null || user.getUsername().length() == 0) {
            throw new UserEmptyUsernameException();
        }
        checkPasswordConstraints(user.getPassword());
        if (user.getRole() == null || (!user.getRole().equals("USER") && !user.getRole().equals("ADMIN"))) {
            throw new UserInvalidRoleException();
        }
    }

    private void checkPasswordConstraints(String password) {
        //Password constraints
        if (password == null || password.length() < 3) {
            throw new UserInvalidPasswordException();
        }
    }
}
