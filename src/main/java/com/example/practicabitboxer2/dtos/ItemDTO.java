package com.example.practicabitboxer2.dtos;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
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
