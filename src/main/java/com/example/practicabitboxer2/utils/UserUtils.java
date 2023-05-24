package com.example.practicabitboxer2.utils;

import com.example.practicabitboxer2.dtos.UserDTO;
import com.example.practicabitboxer2.model.Role;
import com.example.practicabitboxer2.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserUtils {

    private UserUtils() {
        throw new IllegalStateException();
    }

    public static User dtoToEntity(UserDTO dto) {
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

    public static UserDTO entityToDto(User entity) {
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

    public static List<User> dtoToEntities(List<UserDTO> testList) {
        return testList.stream().map(UserUtils::dtoToEntity).collect(Collectors.toList());
    }
}
