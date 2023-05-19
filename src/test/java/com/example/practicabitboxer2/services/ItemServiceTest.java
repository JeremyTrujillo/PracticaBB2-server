package com.example.practicabitboxer2.services;

import com.example.practicabitboxer2.dtos.ItemDTO;
import com.example.practicabitboxer2.model.Item;
import com.example.practicabitboxer2.repositories.ItemRepository;
import com.example.practicabitboxer2.utils.ItemUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.practicabitboxer2.model.ItemState.ACTIVE;
import static com.example.practicabitboxer2.utils.TestUtils.firstItem;
import static com.example.practicabitboxer2.utils.TestUtils.secondItem;
import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @BeforeEach
    void config() {
        this.itemService = new ItemService(itemRepository);
    }

    @Test
    void findAll() {
        List<ItemDTO> testList = newArrayList(firstItem().build(), secondItem().build());
        List<Item> entityList = testList.stream().map(ItemUtils::dtoToEntity).collect(Collectors.toList());
        when(itemRepository.findAll()).thenReturn(entityList);
        List<ItemDTO> all = itemService.findAll();
        assertArrayEquals(testList.toArray(), all.toArray());
    }

    @Test
    void findByItemCode() {
        ItemDTO first = firstItem().build();
        when(itemRepository.findByItemCode(1)).thenReturn(Optional.of(ItemUtils.dtoToEntity(first)));
        when(itemRepository.findByItemCode(3)).thenReturn(Optional.empty());
        assertEquals(first, itemService.findByItemCode(1));
        assertNull(itemService.findByItemCode(3));
    }

    @Test
    void findByState() {
        List<ItemDTO> testList = newArrayList(firstItem().build());
        List<Item> entityList = testList.stream().map(ItemUtils::dtoToEntity).collect(Collectors.toList());
        when(itemRepository.findByState(ACTIVE.getName())).thenReturn(entityList);
        List<ItemDTO> all = itemService.findByState(ACTIVE);
        assertArrayEquals(testList.toArray(), all.toArray());
    }

    @Test
    void saveItem() {
        ItemDTO firstItem = firstItem().build();
        Item firstEntity = ItemUtils.dtoToEntity(firstItem);
        itemService.saveItem(firstItem);
        verify(itemRepository, times(1)).save(firstEntity);
    }

    @Test
    void deleteByItemCode() {
        itemService.deleteByItemCode(1);
        verify(itemRepository, times(1)).deleteByItemCode(1);
    }
}