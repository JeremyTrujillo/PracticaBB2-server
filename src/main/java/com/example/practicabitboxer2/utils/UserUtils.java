package com.example.practicabitboxer2.utils;

import com.example.practicabitboxer2.dtos.UserDTO;
import com.example.practicabitboxer2.model.User;

public class UserUtils {
    public static User dtoToEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        return user;
    }

    public static UserDTO entityToDto(User entity) {
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        return dto;
    }
}
