package com.example.practicabitboxer2.model;

import lombok.Data;

import javax.persistence.*;

import java.util.List;

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

    @ManyToMany(mappedBy = "suppliers")
    private List<Item> items;

}
