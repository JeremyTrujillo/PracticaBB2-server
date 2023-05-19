package com.example.practicabitboxer2.utils;

import com.example.practicabitboxer2.utils.builders.ItemBuilder;
import com.example.practicabitboxer2.utils.builders.UserBuilder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;

import static com.example.practicabitboxer2.model.ItemState.ACTIVE;
import static com.example.practicabitboxer2.model.ItemState.INACTIVE;
import static com.example.practicabitboxer2.model.Role.ADMIN;
import static com.example.practicabitboxer2.model.Role.USER;

public class TestUtils {

    private TestUtils() {
        throw new IllegalStateException();
    }

    public static ItemBuilder firstItem() {
        return ItemBuilder.itemBuilder()
                .withId(1L)
                .withItemCode(1L)
                .withDescription("First item")
                .withPrice(5F)
                .withState(ACTIVE.getName())
                .withCreationDate(Date.from(LocalDate.of(2023, 5, 17)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .withSuppliers(Collections.emptyList())
                .withPriceReductions(Collections.emptyList())
                .withCreator(firstUser().build());
    }

    public static ItemBuilder secondItem() {
        return ItemBuilder.itemBuilder()
                .withId(2L)
                .withItemCode(2L)
                .withDescription("Second item")
                .withPrice(10F)
                .withState(INACTIVE.getName())
                .withCreationDate(Date.from(LocalDate.of(2023, 2, 10)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .withSuppliers(Collections.emptyList())
                .withPriceReductions(Collections.emptyList())
                .withCreator(secondUser().build());
    }

    public static UserBuilder firstUser() {
        return UserBuilder.userBuilder()
                .withId(1L)
                .withUsername("Jeremy")
                .withPassword("pass")
                .withRole(ADMIN.getName());
    }

    public static UserBuilder secondUser() {
        return UserBuilder.userBuilder()
                .withId(2L)
                .withUsername("Daniel")
                .withPassword("pass")
                .withRole(USER.getName());
    }
}
