package com.example.practicabitboxer2.utils.builders;

import com.example.practicabitboxer2.dtos.SupplierDTO;

public class SupplierBuilder {

    private Long id;
    private String name;
    private String country;

    public static SupplierBuilder supplierBuilder() {
        return new SupplierBuilder();
    }

    public SupplierBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public SupplierBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public SupplierBuilder withCountry(String country) {
        this.country = country;
        return this;
    }

    public SupplierDTO build() {
        SupplierDTO supplier = new SupplierDTO();
        supplier.setId(this.id);
        supplier.setName(this.name);
        supplier.setCountry(this.country);
        return supplier;
    }
}
