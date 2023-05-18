package com.example.practicabitboxer2.utils;

import com.example.practicabitboxer2.dtos.ItemDTO;
import com.example.practicabitboxer2.model.Item;
import com.example.practicabitboxer2.model.ItemState;

import java.util.Collections;

public class ItemUtils {

    public static Item dtoToEntity(ItemDTO dto) {
        Item item = new Item();
        item.setItemCode(dto.getItemCode());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        item.setState(ItemState.fromText(dto.getState()));
        item.setCreationDate(dto.getCreationDate());
        item.setSuppliers(Collections.emptyList());
        item.setPriceReductions(Collections.emptyList());
        item.setCreator(null);
        return item;
    }

    public static ItemDTO entityToDto(Item entity, boolean detailed) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setItemCode(entity.getItemCode());
        itemDTO.setDescription(entity.getDescription());
        itemDTO.setPrice(entity.getPrice());
        itemDTO.setState((entity.getState().getName()));
        itemDTO.setCreationDate(entity.getCreationDate());
        itemDTO.setSuppliers(detailed ? SupplierUtils.entitiesToDtos(entity.getSuppliers()) : Collections.emptyList());
        itemDTO.setPriceReductions(detailed
                ? PriceReductionUtils.entitiesToDtos(entity.getPriceReductions())
                : Collections.emptyList());
        itemDTO.setCreator(entity.getCreator() != null ? UserUtils.entityToDto(entity.getCreator()) : null);
        return itemDTO;
    }
}
