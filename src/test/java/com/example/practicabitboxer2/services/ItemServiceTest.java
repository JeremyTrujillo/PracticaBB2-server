package com.example.practicabitboxer2.services;

import com.example.practicabitboxer2.dtos.ItemDTO;
import com.example.practicabitboxer2.dtos.UserDTO;
import com.example.practicabitboxer2.model.Item;
import com.example.practicabitboxer2.model.ItemDeactivator;
import com.example.practicabitboxer2.model.User;
import com.example.practicabitboxer2.repositories.ItemRepository;
import com.example.practicabitboxer2.utils.converters.ItemConverter;
import com.example.practicabitboxer2.utils.converters.UserConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.practicabitboxer2.model.ItemState.ACTIVE;
import static com.example.practicabitboxer2.model.ItemState.DISCONTINUED;
import static com.example.practicabitboxer2.utils.TestUtils.*;
import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    private final ItemConverter itemConverter = new ItemConverter();
    private final UserConverter userConverter = new UserConverter();
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @BeforeEach
    void config() {
        this.itemService = new ItemService(itemRepository);
    }

    @Test
    void findAll() {
        List<ItemDTO> testList = newArrayList(
                firstItem().withSuppliers(Collections.emptyList()).withPriceReductions(Collections.emptyList()).build(),
                secondItem().withSuppliers(Collections.emptyList()).withPriceReductions(Collections.emptyList()).build());
        when(itemRepository.findAll()).thenReturn(itemConverter.dtosToEntities(testList));
        List<ItemDTO> all = itemService.findAll();
        assertArrayEquals(testList.toArray(), all.toArray());
    }

    @Test
    void findById() {
        ItemDTO first = firstItem().build();
        long nonexistentId = 3;
        when(itemRepository.findById(first.getId())).thenReturn(Optional.of(itemConverter.dtoToEntity(first)));
        when(itemRepository.findById(nonexistentId)).thenReturn(Optional.empty());
        assertEquals(first, itemService.findById(first.getId()));
        assertNull(itemService.findById(nonexistentId));
    }

    @Test
    void findByItemCode() {
        ItemDTO first = firstItem().build();
        long nonexistentCode = 3;
        when(itemRepository.findByItemCode(first.getItemCode()))
                .thenReturn(Optional.of(itemConverter.dtoToEntity(first)));
        when(itemRepository.findByItemCode(nonexistentCode)).thenReturn(Optional.empty());
        assertEquals(first, itemService.findByItemCode(first.getItemCode()));
        assertNull(itemService.findByItemCode(nonexistentCode));
    }

    @Test
    void findByState() {
        List<ItemDTO> testList = newArrayList(
                firstItem().withSuppliers(Collections.emptyList()).withPriceReductions(Collections.emptyList()).build());
        List<Item> entityList = testList.stream().map(itemConverter::dtoToEntity).collect(Collectors.toList());
        when(itemRepository.findByState(ACTIVE)).thenReturn(entityList);
        List<ItemDTO> all = itemService.findByState(ACTIVE);
        assertArrayEquals(testList.toArray(), all.toArray());
    }

    @Test
    void saveItem() {
        ItemDTO firstItem = firstItem().build();
        Item firstEntity = itemConverter.dtoToEntity(firstItem);
        itemService.saveItem(firstItem);
        verify(itemRepository, times(1)).save(firstEntity);
    }

    @Test
    void deactivateItem() {
        ItemDTO item = firstItem().build();
        Item itemEntity = itemConverter.dtoToEntity(item);
        UserDTO user = firstUser().build();
        User userEntity = userConverter.dtoToEntity(user);
        String reason = "Reason";
        ArgumentCaptor<Item> argumentCaptor = ArgumentCaptor.forClass(Item.class);
        itemService.deactivateItem(item, user, reason);
        verify(itemRepository, times(1)).save(argumentCaptor.capture());
        itemEntity.setState(DISCONTINUED);
        Item deactivatedItem = argumentCaptor.getValue();
        ItemDeactivator deactivator = deactivatedItem.getDeactivator();
        assertNotNull(deactivator);
        assertEquals(userEntity, deactivator.getDeactivator());
        assertEquals(itemEntity, deactivator.getItem());
        assertEquals(reason, deactivator.getReason());
    }

    @Test
    void deleteByItemCode() {
        itemService.deleteByItemCode(1);
        verify(itemRepository, times(1)).deleteByItemCode(1);
    }
}