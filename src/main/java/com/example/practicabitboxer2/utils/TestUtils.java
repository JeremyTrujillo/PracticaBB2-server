package com.example.practicabitboxer2.utils;

import com.example.practicabitboxer2.utils.builders.ItemBuilder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;

import static com.example.practicabitboxer2.model.ItemState.ACTIVE;
import static com.example.practicabitboxer2.model.ItemState.INACTIVE;

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
                .withCreator(null);
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
                .withCreator(null);
    }
}
