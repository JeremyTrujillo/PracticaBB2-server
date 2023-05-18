package com.example.practicabitboxer2.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "items")
@Data
@Builder
public class Item {

    @Id
    @Column(name = "itemcode")
    @GeneratedValue(strategy = SEQUENCE, generator = "item_id_seq")
    private Long itemCode;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price")
    private Float price;

    @Column(name = "state")
    private ItemState state;

    @ManyToMany
    @JoinTable(
            name = "items_suppliers",
            joinColumns = @JoinColumn(name = "supplier_id"),
            inverseJoinColumns = @JoinColumn(name = "item_code"))
    private List<Supplier> suppliers;

    @ManyToMany
    @JoinTable(
            name = "items_pricereductions",
            joinColumns = @JoinColumn(name = "pricereduction_id"),
            inverseJoinColumns = @JoinColumn(name = "item_code"))
    private List<PriceReduction> priceReductions;

    @Column(name = "creationDate")
    private Date creationDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "creator")
    private User creator;

    public Float getCurrentPrice() {
        return null;
    }
}
