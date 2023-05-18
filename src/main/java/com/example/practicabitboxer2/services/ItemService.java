package com.example.practicabitboxer2.services;

import com.example.practicabitboxer2.dtos.ItemDTO;
import com.example.practicabitboxer2.model.Item;
import com.example.practicabitboxer2.repositories.ItemRepository;
import com.example.practicabitboxer2.utils.ItemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    ItemRepository repository;

    public List<ItemDTO> findAll() {
        List<Item> items = repository.findAll();
        return items.stream().map(ItemUtils::entityToDto).collect(Collectors.toList());
    }

    public ItemDTO findById(long id) {
        Item item = repository.findById(id).orElse(null);
        if (item == null) {
            return null;
        }
        return ItemUtils.entityToDto(item);
    }
}
