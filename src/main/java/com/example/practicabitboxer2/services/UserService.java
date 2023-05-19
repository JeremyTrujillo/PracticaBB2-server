package com.example.practicabitboxer2.services;

import com.example.practicabitboxer2.dtos.ItemDTO;
import com.example.practicabitboxer2.dtos.UserDTO;
import com.example.practicabitboxer2.model.User;
import com.example.practicabitboxer2.repositories.UserRepository;
import com.example.practicabitboxer2.utils.ItemUtils;
import com.example.practicabitboxer2.utils.UserUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final @NonNull UserRepository repository;

    public void login() {

    }

    public UserDTO findByUsername(String username) {
        User user = repository.findByUsername(username).orElse(null);
        if (user == null) {
            return null;
        }
        return UserUtils.entityToDto(user);
    }

    public List<UserDTO> findAll() {
        List<User> users = repository.findAll();
        return users.stream().map(UserUtils::entityToDto).collect(Collectors.toList());
    }

    public void saveUser(UserDTO user) {
        repository.save(UserUtils.dtoToEntity(user));
    }

    public void deleteByUsername(String username) {
        repository.deleteByUsername(username);
    }
}
