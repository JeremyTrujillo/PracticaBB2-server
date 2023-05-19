package com.example.practicabitboxer2.model;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    Role(String name) {
        this.name = name;
    }

    private final String name;

    public String getName() {
        return name;
    }

    public static Role fromText(String text) {
        if (text == null) {
            return USER;
        }
        for (Role state : values()) {
            if (state.getName().equals(text.toUpperCase())) {
                return state;
            }
        }
        return USER;
    }
}
