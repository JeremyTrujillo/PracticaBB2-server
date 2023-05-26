package com.example.practicabitboxer2.services;

import com.example.practicabitboxer2.dtos.ItemDTO;
import com.example.practicabitboxer2.dtos.UserDTO;
import com.example.practicabitboxer2.model.Item;
import com.example.practicabitboxer2.model.ItemDeactivator;
import com.example.practicabitboxer2.model.ItemState;
import com.example.practicabitboxer2.model.User;
import com.example.practicabitboxer2.repositories.ItemRepository;
import com.example.practicabitboxer2.utils.converters.ItemConverter;
import com.example.practicabitboxer2.utils.converters.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.practicabitboxer2.model.ItemState.DISCONTINUED;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemService {

    private final @NonNull ItemRepository repository;
    private final ItemConverter itemConverter = new ItemConverter();
    private final UserConverter userConverter = new UserConverter();

    public ItemDTO findById(long id) {
        Item item = repository.findById(id).orElse(null);
        if (item == null) {
            return null;
        }
        return itemConverter.entityToDto(item);
    }

    @Transactional
    public ItemDTO findByItemCode(long itemCode) {
        Item item = repository.findByItemCode(itemCode).orElse(null);
        if (item == null) {
            return null;
        }
        return itemConverter.entityToDto(item);
    }

    public List<ItemDTO> findAll() {
        List<Item> items = repository.findAll();
        return itemConverter.entitiesToDtosSimplified(items);
    }

    public List<ItemDTO> findByState(ItemState state) {
        List<Item> items = repository.findByState(state);
        return itemConverter.entitiesToDtosSimplified(items);
    }

    public List<ItemDTO> findCheapestPerSupplier() {
        List<Item> cheapestPerSupplier = repository.findCheapestPerSupplier();
        return itemConverter.entitiesToDtos(cheapestPerSupplier);
    }

    public void saveItem(ItemDTO item) {
        repository.save(itemConverter.dtoToEntity(item));
    }

    public void deactivateItem(ItemDTO itemDTO, UserDTO userDTO, String reason) {
        Item item = itemConverter.dtoToEntity(itemDTO);
        item.setState(DISCONTINUED);
        User user = userConverter.dtoToEntity(userDTO);
        ItemDeactivator itemDeactivator = new ItemDeactivator();
        itemDeactivator.setItem(item);
        itemDeactivator.setDeactivator(user);
        itemDeactivator.setReason(reason);
        item.setDeactivator(itemDeactivator);
        repository.save(item);
    }

    public void deleteByItemCode(long itemCode) {
        repository.deleteByItemCode(itemCode);
    }
}
