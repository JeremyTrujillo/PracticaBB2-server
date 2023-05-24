package com.example.practicabitboxer2.utils;

import com.example.practicabitboxer2.dtos.ItemDTO;
import com.example.practicabitboxer2.model.Item;
import com.example.practicabitboxer2.model.ItemState;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ItemUtils {

    private ItemUtils() {
        throw new IllegalStateException();
    }

    public static Item dtoToEntity(ItemDTO dto) {
        Item item = new Item();
        item.setId(dto.getId());
        item.setItemCode(dto.getItemCode());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        item.setState(ItemState.fromText(dto.getState()));
        item.setCreationDate(dto.getCreationDate());
        item.setSuppliers(SupplierUtils.dtosToEntities(dto.getSuppliers()));
        item.setPriceReductions(PriceReductionUtils.dtosToEntities(dto.getPriceReductions()));
        item.setCreator(UserUtils.dtoToEntity(dto.getCreator()));
        return item;
    }

    public static ItemDTO entityToDto(Item entity, boolean detailed) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(entity.getId());
        itemDTO.setItemCode(entity.getItemCode());
        itemDTO.setDescription(entity.getDescription());
        itemDTO.setPrice(entity.getPrice());
        itemDTO.setState((entity.getState().getName()));
        itemDTO.setCreationDate(entity.getCreationDate());
        itemDTO.setSuppliers(detailed ? SupplierUtils.entitiesToDtos(entity.getSuppliers()) : Collections.emptyList());
        itemDTO.setPriceReductions(detailed
                ? PriceReductionUtils.entitiesToDtos(entity.getPriceReductions())
                : Collections.emptyList());
        itemDTO.setCreator(UserUtils.entityToDto(entity.getCreator()));
        return itemDTO;
    }


    public static List<Item> dtoToEntities(List<ItemDTO> testList) {
        return testList.stream().map(ItemUtils::dtoToEntity).collect(Collectors.toList());
    }
}
