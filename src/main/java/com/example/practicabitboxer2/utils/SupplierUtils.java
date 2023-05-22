package com.example.practicabitboxer2.utils;

import com.example.practicabitboxer2.dtos.SupplierDTO;
import com.example.practicabitboxer2.model.Supplier;

import java.util.List;
import java.util.stream.Collectors;

public class SupplierUtils {

    private SupplierUtils() {
        throw new IllegalStateException();
    }

    public static List<SupplierDTO> entitiesToDtos(List<Supplier> entities) {
        return entities.stream().map(SupplierUtils::entityToDto).collect(Collectors.toList());
    }

    public static List<Supplier> dtosToEntities(List<SupplierDTO> dtos) {
        return dtos.stream().map(SupplierUtils::dtoToEntity).collect(Collectors.toList());
    }

    public static SupplierDTO entityToDto(Supplier entity) {
        SupplierDTO dto = new SupplierDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCountry(entity.getCountry());
        return dto;
    }

    public static Supplier dtoToEntity(SupplierDTO dto) {
        Supplier supplier = new Supplier();
        supplier.setId(dto.getId());
        supplier.setName(dto.getName());
        supplier.setCountry(dto.getCountry());
        return supplier;
    }
}
