package com.example.practicabitboxer2.model;

public enum ItemState {
    ACTIVE("ACTIVE"),
    DISCONTINUED("DISCONTINUED");

    ItemState(String name) {
        this.name = name;
    }

    private final String name;

    public String getName() {
        return name;
    }

    public static ItemState fromText(String text) {
        if (text == null) {
            return null;
        }
        for (ItemState state : values()) {
            if (state.getName().equals(text.toUpperCase())) {
                return state;
            }
        }
        return null;
    }
}
