package com.example.practicabitboxer2.dtos;

import lombok.Data;

import java.util.List;

@Data
public class SupplierDTO {
    private Long id;
    private String name;
    private String country;
    private List<ItemDTO> items;
}
