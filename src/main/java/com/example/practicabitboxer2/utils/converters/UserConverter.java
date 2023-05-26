package com.example.practicabitboxer2.utils.converters;

import com.example.practicabitboxer2.dtos.UserDTO;
import com.example.practicabitboxer2.model.Role;
import com.example.practicabitboxer2.model.User;
import com.example.practicabitboxer2.utils.Converter;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter implements Converter<User, UserDTO> {

    public User dtoToEntity(UserDTO dto) {
        User user = new User();
        if (dto == null) {
            return user;
        }
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(Role.fromText(dto.getRole()));
        return user;
    }

    public UserDTO entityToDto(User entity) {
        UserDTO dto = new UserDTO();
        if (entity == null) {
            return dto;
        }
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole().getName());
        return dto;
    }

    public List<User> dtosToEntities(List<UserDTO> testList) {
        return testList.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

    public List<UserDTO> entitiesToDtos(List<User> users) {
        return users.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
