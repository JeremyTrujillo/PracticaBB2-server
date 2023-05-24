package com.example.practicabitboxer2.controller;

import com.example.practicabitboxer2.dtos.ItemDTO;
import com.example.practicabitboxer2.exceptions.*;
import com.example.practicabitboxer2.services.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static com.example.practicabitboxer2.model.ItemState.ACTIVE;
import static com.example.practicabitboxer2.model.ItemState.DISCONTINUED;
import static com.example.practicabitboxer2.utils.TestUtils.firstItem;
import static com.example.practicabitboxer2.utils.TestUtils.secondItem;
import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    private ItemController itemController;

    @Mock
    private ItemService itemService;

    @BeforeEach
    void config() {
        this.itemController = new ItemController(itemService);
    }

    @Test
    void findAll() {
        List<ItemDTO> testList = newArrayList(firstItem().build(), secondItem().build());
        when(itemService.findAll()).thenReturn(testList);
        List<ItemDTO> all = itemController.findAll().getBody();
        assertNotNull(all);
        assertArrayEquals(testList.toArray(), all.toArray());
    }

    @Test
    void findActive() {
        List<ItemDTO> testList = newArrayList(firstItem().build());
        when(itemService.findByState(ACTIVE)).thenReturn(testList);
        List<ItemDTO> all = itemController.findActive().getBody();
        assertNotNull(all);
        assertArrayEquals(testList.toArray(), all.toArray());
    }

    @Test
    void findDiscontinued() {
        List<ItemDTO> testList = newArrayList(secondItem().build());
        when(itemService.findByState(DISCONTINUED)).thenReturn(testList);
        List<ItemDTO> all = itemController.findDiscontinued().getBody();
        assertNotNull(all);
        assertArrayEquals(testList.toArray(), all.toArray());
    }

    @Test
    void findByItemCode() {
        when(itemService.findByItemCode(1)).thenReturn(firstItem().build());
        when(itemService.findByItemCode(3)).thenReturn(null);
        ItemDTO item = itemController.findByItemCode(1).getBody();
        assertEquals(item, firstItem().build());
        assertThrows(ItemNotFoundException.class,
                () -> itemController.findByItemCode(3));
    }

    @Test
    void createInvalidItem() {
        assertThrows(ItemEmptyException.class,
                () -> itemController.createItem(null));
        ItemDTO invalidCodeItem = firstItem().withItemCode(null).build();
        assertThrows(ItemEmptyCodeException.class,
                () -> itemController.createItem(invalidCodeItem));
        ItemDTO invalidDescriptionItem = firstItem().withDescription(null).build();
        assertThrows(ItemEmptyDescriptionException.class,
                () -> itemController.createItem(invalidDescriptionItem));
    }

    @Test
    void createAlreadyExistentItem() {
        ItemDTO firstItem = firstItem().build();
        when(itemService.findByItemCode(1)).thenReturn(firstItem);
        assertThrows(ItemCodeAlreadyExistsException.class,
                () -> itemController.createItem(firstItem));
    }

    @Test
    void createValidItem() {
        List<ItemDTO> testList = newArrayList();
        ItemDTO firstItem = firstItem().build();
        when(itemService.findAll()).thenReturn(testList);
        doAnswer(invocationOnMock -> testList.add(firstItem)).when(itemService).saveItem(firstItem);
        itemController.createItem(firstItem);
        List<ItemDTO> all = itemController.findAll().getBody();
        assertNotNull(all);
        assertEquals(1, all.size());
        assertEquals(ACTIVE.getName(), all.get(0).getState());
        LocalDate currentDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date itemCreationDate = all.get(0).getCreationDate();
        LocalDate creationDate = itemCreationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        assertEquals(currentDate, creationDate);
    }

    @Test
    void editInvalidItem() {
        when(itemService.findByItemCode(2)).thenReturn(secondItem().build());
        assertThrows(ItemEmptyException.class,
                () -> itemController.editItem(null, 1));
        ItemDTO invalidCodeItem = firstItem().withItemCode(null).build();
        assertThrows(ItemEmptyCodeException.class,
                () -> itemController.editItem(invalidCodeItem, 1));
        ItemDTO invalidDescriptionItem = firstItem().withDescription(null).build();
        assertThrows(ItemEmptyDescriptionException.class,
                () -> itemController.editItem(invalidDescriptionItem, 1));
        ItemDTO firstItem = firstItem().build();
        assertThrows(ItemInvalidCodeException.class,
                () -> itemController.editItem(firstItem, 2));
        ItemDTO discontinuedItem = secondItem().build();
        assertThrows(ItemInvalidStateException.class,
                () -> itemController.editItem(discontinuedItem, 2));
    }

    @Test
    void editNonexistentItem() {
        when(itemService.findByItemCode(1)).thenReturn(null);
        ItemDTO firstItem = firstItem().build();
        assertThrows(ItemNotFoundException.class,
                () -> itemController.editItem(firstItem, 1));
    }

    @Test
    void editValidItem() {
        ItemDTO item = firstItem().build();
        List<ItemDTO> testList = newArrayList(item);
        when(itemService.findAll()).thenReturn(testList);
        when(itemService.findByItemCode(1)).thenReturn(item);
        doAnswer(invocationOnMock -> testList.set(0, item)).when(itemService).saveItem(item);
        List<ItemDTO> all = itemController.findAll().getBody();
        assertNotNull(all);
        assertEquals(1, all.size());
        assertEquals(firstItem().build().getDescription(), all.get(0).getDescription());
        item.setDescription("editValidItem item");
        itemController.editItem(item, 1);
        all = itemController.findAll().getBody();
        assertNotNull(all);
        assertEquals(1, all.size());
        assertEquals("editValidItem item", all.get(0).getDescription());
    }

    @Test
    void deleteNonexistentItem() {
        when(itemService.findByItemCode(1)).thenReturn(null);
        assertThrows(ItemNotFoundException.class,
                () -> itemController.deleteByItemCode(1));
    }

    @Test
    void deleteValidItem() {
        ItemDTO item = firstItem().build();
        List<ItemDTO> testList = newArrayList(item);
        when(itemService.findAll()).thenReturn(testList);
        when(itemService.findByItemCode(1)).thenReturn(item);
        doAnswer(invocationOnMock -> testList.remove(0)).when(itemService).deleteByItemCode(1);
        List<ItemDTO> all = itemController.findAll().getBody();
        assertNotNull(all);
        assertEquals(1, all.size());
        itemController.deleteByItemCode(1);
        all = itemController.findAll().getBody();
        assertNotNull(all);
        assertEquals(0, all.size());
    }
}