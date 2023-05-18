package com.example.practicabitboxer2.utils;

import com.example.practicabitboxer2.dtos.PriceReductionDTO;
import com.example.practicabitboxer2.model.PriceReduction;

import java.util.List;
import java.util.stream.Collectors;

public class PriceReductionUtils {

    public static List<PriceReductionDTO> entitiesToDtos(List<PriceReduction> entities) {
        return entities.stream().map(PriceReductionUtils::entityToDto).collect(Collectors.toList());
    }

    public static PriceReductionDTO entityToDto(PriceReduction entity) {
        PriceReductionDTO dto = new PriceReductionDTO();
        dto.setId(entity.getId());
        dto.setReducedPrice(entity.getReducedPrice());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        return dto;
    }
}
