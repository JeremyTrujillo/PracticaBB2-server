package com.example.practicabitboxer2.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class PriceReductionDTO {
    private Long id;
    private Float reducedPrice;
    private Date startDate;
    private Date endDate;
}
