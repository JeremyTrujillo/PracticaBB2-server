package com.example.practicabitboxer2.dtos;

import lombok.Data;

@Data
public class ItemDeactivatorDTO {
    private Long itemId;
    private Long userId;
    private String reason;
}
