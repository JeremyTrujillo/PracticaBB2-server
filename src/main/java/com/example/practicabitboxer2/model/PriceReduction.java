package com.example.practicabitboxer2.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "pricereduction")
@Data
public class PriceReduction {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = SEQUENCE, generator = "pricereduction_id_seq")
    private Long id;

    @Column(name = "reducedprice")
    private Float reducedPrice;

    @Column(name = "startdate")
    private Date startDate;

    @Column(name = "enddate")
    private Date endDate;
}
