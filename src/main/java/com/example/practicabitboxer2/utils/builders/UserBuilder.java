package com.example.practicabitboxer2.utils.builders;

import com.example.practicabitboxer2.dtos.UserDTO;

public class UserBuilder {

    private Long id;
    private String username;
    private String password;
    private String role;

    public static UserBuilder userBuilder() {
        return new UserBuilder();
    }

    public UserBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withRole(String role) {
        this.role = role;
        return this;
    }

    public UserDTO build() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(this.id);
        userDTO.setUsername(this.username);
        userDTO.setPassword(this.password);
        userDTO.setRole(this.role);
        return userDTO;
    }
}
