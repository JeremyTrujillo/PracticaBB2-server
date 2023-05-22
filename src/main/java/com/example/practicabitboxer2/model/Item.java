package com.example.practicabitboxer2.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "items")
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "item_id_seq")
    private Long id;

    @Column(name = "itemcode", unique = true, nullable = false)
    private Long itemCode;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price")
    private Float price;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private ItemState state;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "items_suppliers",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id"))
    private List<Supplier> suppliers;

    @ManyToMany
    @JoinTable(
            name = "items_pricereductions",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "pricereduction_id"))
    private List<PriceReduction> priceReductions;

    @Column(name = "creationdate")
    private Date creationDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "creator")
    private User creator;

    public Float getCurrentPrice() {
        return null;
    }
}
