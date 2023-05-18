package com.example.practicabitboxer2.services;

import com.example.practicabitboxer2.dtos.ItemDTO;
import com.example.practicabitboxer2.model.Item;
import com.example.practicabitboxer2.model.ItemState;
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

    public ItemDTO findByItemCode(long id) {
        Item item = repository.findByItemCode(id).orElse(null);
        if (item == null) {
            return null;
        }
        return ItemUtils.entityToDto(item, true);
    }

    public List<ItemDTO> findAll() {
        List<Item> items = repository.findAll();
        return items.stream().map(item -> ItemUtils.entityToDto(item, false)).collect(Collectors.toList());
    }

    public List<ItemDTO> findByState(ItemState state) {
        List<Item> items = repository.findByState(state.getName());
        return items.stream().map(item -> ItemUtils.entityToDto(item, false)).collect(Collectors.toList());
    }

    public void saveItem(ItemDTO item) {
        repository.save(ItemUtils.dtoToEntity(item));
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
