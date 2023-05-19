package com.example.practicabitboxer2.controller;

import com.example.practicabitboxer2.dtos.UserDTO;
import com.example.practicabitboxer2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping
    public void login(@RequestBody UserDTO user) {
        service.login();
    }

    @GetMapping
    public List<UserDTO> findAll() {
        return service.findAll();
    }
}
