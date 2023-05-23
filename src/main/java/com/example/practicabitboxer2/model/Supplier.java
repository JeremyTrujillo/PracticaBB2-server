package com.example.practicabitboxer2.model;

import lombok.Data;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "suppliers")
@Data
public class Supplier {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = SEQUENCE, generator = "supplier_id_seq")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(name, supplier.name) && Objects.equals(country, supplier.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country);
    }
}
