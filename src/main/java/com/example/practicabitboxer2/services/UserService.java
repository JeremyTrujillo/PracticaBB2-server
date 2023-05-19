package com.example.practicabitboxer2.services;

import com.example.practicabitboxer2.dtos.UserDTO;
import com.example.practicabitboxer2.model.User;
import com.example.practicabitboxer2.repositories.UserRepository;
import com.example.practicabitboxer2.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public void login() {

    }

    public List<UserDTO> findAll() {
        List<User> users = repository.findAll();
        return users.stream().map(UserUtils::entityToDto).collect(Collectors.toList());
    }
}
