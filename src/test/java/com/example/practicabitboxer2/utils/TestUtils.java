package com.example.practicabitboxer2.utils;

import com.example.practicabitboxer2.utils.builders.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;

import static com.example.practicabitboxer2.model.ItemState.ACTIVE;
import static com.example.practicabitboxer2.model.ItemState.DISCONTINUED;
import static com.example.practicabitboxer2.model.Role.ADMIN;
import static com.example.practicabitboxer2.model.Role.USER;
import static org.assertj.core.util.Lists.newArrayList;

public class TestUtils {

    private static final String PASSWORD = "password";

    private TestUtils() {
        throw new IllegalStateException();
    }

    public static UserBuilder firstUser() {
        return UserBuilder.userBuilder()
                .withId(1L)
                .withUsername("Jeremy")
                .withPassword(PASSWORD)
                .withRole(ADMIN.getName());
    }

    public static UserBuilder secondUser() {
        return UserBuilder.userBuilder()
                .withId(2L)
                .withUsername("Daniel")
                .withPassword(PASSWORD)
                .withRole(USER.getName());
    }

    public static UserBuilder newUser() {
        return UserBuilder.userBuilder()
                .withId(3L)
                .withUsername("NewUser")
                .withPassword(PASSWORD)
                .withRole(USER.getName());
    }

    public static ItemBuilder firstItem() {
        return ItemBuilder.itemBuilder()
                .withId(1L)
                .withItemCode(1L)
                .withDescription("First item")
                .withPrice(5F)
                .withState(ACTIVE.getName())
                .withCreationDate(Date.from(LocalDate.of(2023, 5, 19)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .withSuppliers(newArrayList(firstSupplier().build(), secondSupplier().build()))
                .withPriceReductions(newArrayList(secondPriceReduction().build()))
                .withCreator(firstUser().build());
    }

    public static ItemBuilder secondItem() {
        return ItemBuilder.itemBuilder()
                .withId(2L)
                .withItemCode(2L)
                .withDescription("Second item")
                .withPrice(10F)
                .withState(DISCONTINUED.getName())
                .withCreationDate(Date.from(LocalDate.of(2023, 2, 10)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .withSuppliers(Collections.emptyList())
                .withPriceReductions(newArrayList(firstPriceReduction().build()))
                .withCreator(secondUser().build());
    }

    public static ItemBuilder newItem() {
        return ItemBuilder.itemBuilder()
                .withId(5L)
                .withItemCode(5L)
                .withDescription("New item")
                .withPrice(1F)
                .withState(ACTIVE.getName())
                .withCreationDate(Date.from(LocalDate.of(2023, 5, 24)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .withSuppliers(Collections.emptyList())
                .withPriceReductions(Collections.emptyList())
                .withCreator(firstUser().build());
    }

    public static SupplierBuilder firstSupplier() {
        return SupplierBuilder.supplierBuilder()
                .withId(1L)
                .withName("First supplier")
                .withCountry("Spain");
    }

    public static SupplierBuilder secondSupplier() {
        return SupplierBuilder.supplierBuilder()
                .withId(2L)
                .withName("Second supplier")
                .withCountry("Spain");
    }

    public static PriceReductionBuilder firstPriceReduction() {
        return PriceReductionBuilder.priceReductionBuilder()
                .withId(1L)
                .withReducedPrice(5F)
                .withStartDate(Date.from(LocalDate.of(2023, 5, 17)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .withEndDate(Date.from(LocalDate.of(2023, 5, 19)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    public static PriceReductionBuilder secondPriceReduction() {
        return PriceReductionBuilder.priceReductionBuilder()
                .withId(2L)
                .withReducedPrice(2F)
                .withStartDate(Date.from(LocalDate.of(2023, 5, 20)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .withEndDate(Date.from(LocalDate.of(2023, 5, 22)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    public static ItemDeactivatorBuilder firstItemDeactivator() {
        return ItemDeactivatorBuilder.itemDeactivatorBuilder()
                .withItemId(1L)
                .withUserId(1L)
                .withReason("Discontinued");
    }

}
