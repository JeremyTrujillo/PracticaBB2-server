package com.example.practicabitboxer2.utils;

import com.example.practicabitboxer2.dtos.ItemDTO;
import com.example.practicabitboxer2.model.Item;
import com.example.practicabitboxer2.model.ItemState;

import java.util.Collections;

public class ItemUtils {

    public static Item dtoToEntity(ItemDTO dto) {
        return Item.builder()
                .itemCode(dto.getItemCode())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .state(ItemState.fromText(dto.getState()))
                .creationDate(dto.getCreationDate())
                .suppliers(Collections.emptyList())
                .priceReductions(Collections.emptyList())
                .creator(null)
                .build();
    }

    public static ItemDTO entityToDto(Item entity) {
        return ItemDTO.builder()
                .itemCode(entity.getItemCode())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .state((entity.getState().getName()))
                .creationDate(entity.getCreationDate())
                .suppliers(Collections.emptyList())
                .priceReductions(Collections.emptyList())
                .creator(null)
                .build();
    }
}
