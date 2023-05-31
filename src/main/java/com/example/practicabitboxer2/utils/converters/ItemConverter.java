package com.example.practicabitboxer2.utils.converters;

import com.example.practicabitboxer2.dtos.ItemDTO;
import com.example.practicabitboxer2.model.Item;
import com.example.practicabitboxer2.model.ItemState;
import com.example.practicabitboxer2.utils.Converter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ItemConverter implements Converter<Item, ItemDTO> {

    private final SupplierConverter supplierConverter = new SupplierConverter();
    private final PriceReductionConverter priceReductionConverter = new PriceReductionConverter();
    private final UserConverter userConverter = new UserConverter();

    public Item dtoToEntity(ItemDTO dto) {
        Item item = new Item();
        item.setId(dto.getId());
        item.setItemCode(dto.getItemCode());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        item.setState(ItemState.fromText(dto.getState()));
        item.setCreationDate(dto.getCreationDate());
        item.setSuppliers(supplierConverter.dtosToEntities(dto.getSuppliers()));
        item.setPriceReductions(priceReductionConverter.dtosToEntities(dto.getPriceReductions()));
        item.setCreator(userConverter.dtoToEntity(dto.getCreator()));
        return item;
    }

    public ItemDTO entityToDto(Item entity) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(entity.getId());
        itemDTO.setItemCode(entity.getItemCode());
        itemDTO.setDescription(entity.getDescription());
        itemDTO.setPrice(entity.getPrice());
        itemDTO.setState((entity.getState().getName()));
        itemDTO.setCreationDate(entity.getCreationDate());
        itemDTO.setSuppliers(supplierConverter.entitiesToDtos(entity.getSuppliers()));
        itemDTO.setPriceReductions(priceReductionConverter.entitiesToDtos(entity.getPriceReductions()));
        itemDTO.setCreator(userConverter.entityToDto(entity.getCreator()));
        return itemDTO;
    }

    public ItemDTO entityToDtoSimplified(Item entity) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(entity.getId());
        itemDTO.setItemCode(entity.getItemCode());
        itemDTO.setDescription(entity.getDescription());
        itemDTO.setPrice(entity.getPrice());
        itemDTO.setState((entity.getState().getName()));
        itemDTO.setCreationDate(entity.getCreationDate());
        itemDTO.setSuppliers(Collections.emptyList());
        itemDTO.setPriceReductions(Collections.emptyList());
        itemDTO.setCreator(userConverter.entityToDto(entity.getCreator()));
        return itemDTO;
    }

    public List<Item> dtosToEntities(List<ItemDTO> dtos) {
        return dtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

    public List<ItemDTO> entitiesToDtos(List<Item> entities) {
        return entities.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<ItemDTO> entitiesToDtosSimplified(List<Item> entities) {
        return entities.stream().map(this::entityToDtoSimplified).collect(Collectors.toList());
    }
}
