package com.example.practicabitboxer2.utils.converters;

import com.example.practicabitboxer2.dtos.SupplierDTO;
import com.example.practicabitboxer2.model.Supplier;
import com.example.practicabitboxer2.utils.Converter;

import java.util.List;
import java.util.stream.Collectors;

public class SupplierConverter implements Converter<Supplier, SupplierDTO> {

    public Supplier dtoToEntity(SupplierDTO dto) {
        Supplier supplier = new Supplier();
        supplier.setId(dto.getId());
        supplier.setName(dto.getName());
        supplier.setCountry(dto.getCountry());
        return supplier;
    }

    public SupplierDTO entityToDto(Supplier entity) {
        SupplierDTO dto = new SupplierDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCountry(entity.getCountry());
        return dto;
    }

    public List<Supplier> dtosToEntities(List<SupplierDTO> dtos) {
        return dtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

    public List<SupplierDTO> entitiesToDtos(List<Supplier> entities) {
        return entities.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
