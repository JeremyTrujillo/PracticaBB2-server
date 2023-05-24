package com.example.practicabitboxer2.controller;

import com.example.practicabitboxer2.dtos.JwtDTO;
import com.example.practicabitboxer2.dtos.UserDTO;
import com.example.practicabitboxer2.exceptions.*;
import com.example.practicabitboxer2.security.CustomUserDetails;
import com.example.practicabitboxer2.security.JwtGenerator;
import com.example.practicabitboxer2.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final @NonNull UserService service;
    private final @NonNull AuthenticationManager authenticationManager;
    private final @NonNull JwtGenerator jwtGenerator;

    @PreAuthorize("permitAll")
    @PostMapping(value = "/login")
    public ResponseEntity<JwtDTO> login(@RequestBody UserDTO user) {
        checkUserConstraints(user);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        return new ResponseEntity<>(new JwtDTO(token, details.getUsername(), details.getAuthorities()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserDTO user) {
        checkUserConstraints(user);
        if (user.getRole() == null || (!user.getRole().equals("USER") && !user.getRole().equals("ADMIN"))) {
            throw new UserInvalidRoleException();
        }
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
        if (!StringUtils.hasText(user.getUsername())) {
            throw new UserEmptyUsernameException();
        }
        checkPasswordConstraints(user.getPassword());
    }

    private void checkPasswordConstraints(String password) {
        if (!StringUtils.hasText(password)) {
            throw new UserInvalidPasswordException();
        }
    }
}
