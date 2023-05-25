package com.example.practicabitboxer2.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class PriceReductionDTO {
    private Long id;
    private Float reducedPrice;
    private Date startDate;
    private Date endDate;

    public int compareTo(PriceReductionDTO dto) {
        int i = this.startDate.compareTo(dto.startDate);
        if (i != 0) {
            return i;
        }
        return this.endDate.compareTo(dto.endDate);
    }
}
