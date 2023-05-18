package com.example.practicabitboxer2.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class PriceReductionDTO {
    private Long id;
    private Float reducedPrice;
    private Date startDate;
    private Date endDate;
    private List<ItemDTO> items;
}
