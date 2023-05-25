package com.example.practicabitboxer2.model;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "item_deactivator")
@Data
public class ItemDeactivator {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "item_deactivator_id_seq")
    private Long id;

    @OneToOne(fetch = LAZY, orphanRemoval = true, optional = false)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User deactivator;

    @Column(name = "reason")
    private String reason;
}
