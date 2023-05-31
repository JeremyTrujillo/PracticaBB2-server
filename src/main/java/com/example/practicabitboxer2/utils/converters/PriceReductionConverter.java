package com.example.practicabitboxer2.utils.converters;

import com.example.practicabitboxer2.dtos.PriceReductionDTO;
import com.example.practicabitboxer2.model.PriceReduction;
import com.example.practicabitboxer2.utils.Converter;

import java.util.List;
import java.util.stream.Collectors;

public class PriceReductionConverter implements Converter<PriceReduction, PriceReductionDTO> {

    public PriceReduction dtoToEntity(PriceReductionDTO priceReductionDTO) {
        PriceReduction priceReduction = new PriceReduction();
        priceReduction.setId(priceReductionDTO.getId());
        priceReduction.setReducedPrice(priceReductionDTO.getReducedPrice());
        priceReduction.setStartDate(priceReductionDTO.getStartDate());
        priceReduction.setEndDate(priceReductionDTO.getEndDate());
        return priceReduction;
    }

    public PriceReductionDTO entityToDto(PriceReduction entity) {
        PriceReductionDTO dto = new PriceReductionDTO();
        dto.setId(entity.getId());
        dto.setReducedPrice(entity.getReducedPrice());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        return dto;
    }

    public List<PriceReduction> dtosToEntities(List<PriceReductionDTO> priceReductions) {
        return priceReductions.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

    public List<PriceReductionDTO> entitiesToDtos(List<PriceReduction> entities) {
        return entities.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
