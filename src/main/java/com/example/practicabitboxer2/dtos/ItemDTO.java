package com.example.practicabitboxer2.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class ItemDTO {
    private Long itemCode;
    private String description;
    private Float price;
    private String state;
    private List<SupplierDTO> suppliers;
    private List<PriceReductionDTO> priceReductions;
    private Date creationDate;
    private UserDTO creator;
}
