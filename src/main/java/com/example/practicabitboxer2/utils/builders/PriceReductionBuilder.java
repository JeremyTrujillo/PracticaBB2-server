package com.example.practicabitboxer2.utils.builders;

import com.example.practicabitboxer2.dtos.PriceReductionDTO;

import java.util.Date;

public class PriceReductionBuilder {

    private Long id;
    private Float reducedPrice;
    private Date startDate;
    private Date endDate;

    public static PriceReductionBuilder priceReductionBuilder() {
        return new PriceReductionBuilder();
    }

    public PriceReductionBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public PriceReductionBuilder withReducedPrice(Float reducedPrice) {
        this.reducedPrice = reducedPrice;
        return this;
    }

    public PriceReductionBuilder withStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public PriceReductionBuilder withEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public PriceReductionDTO build() {
        PriceReductionDTO priceReduction = new PriceReductionDTO();
        priceReduction.setId(this.id);
        priceReduction.setReducedPrice(this.reducedPrice);
        priceReduction.setStartDate(this.startDate);
        priceReduction.setEndDate(this.endDate);
        return priceReduction;
    }
}
