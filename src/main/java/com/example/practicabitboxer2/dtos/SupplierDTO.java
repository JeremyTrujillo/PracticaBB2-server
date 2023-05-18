package com.example.practicabitboxer2.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SupplierDTO {
    private Long id;
    private String name;
    private String country;
    private List<ItemDTO> items;
}
