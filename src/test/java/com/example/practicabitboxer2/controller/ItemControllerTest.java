package com.example.practicabitboxer2.controller;

import com.example.practicabitboxer2.dtos.ItemDTO;
import com.example.practicabitboxer2.dtos.ItemDeactivatorDTO;
import com.example.practicabitboxer2.exceptions.*;
import com.example.practicabitboxer2.services.ItemService;
import com.example.practicabitboxer2.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static com.example.practicabitboxer2.model.ItemState.ACTIVE;
import static com.example.practicabitboxer2.model.ItemState.DISCONTINUED;
import static com.example.practicabitboxer2.utils.TestUtils.*;
import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    private ItemController itemController;

    @Mock
    private ItemService itemService;

    @Mock
    private UserService userService;

    @BeforeEach
    void config() {
        this.itemController = new ItemController(itemService, userService);
    }

    @Test
    void findAll() {
        List<ItemDTO> testList = newArrayList(firstItem().build(), secondItem().build());
        when(itemService.findAll()).thenReturn(testList);
        ResponseEntity<List<ItemDTO>> findAll = itemController.findAll();
        List<ItemDTO> all = findAll.getBody();
        assertNotNull(all);
        assertArrayEquals(testList.toArray(), all.toArray());
        HttpStatus statusCode = findAll.getStatusCode();
        assertEquals(OK, statusCode);
    }

    @Test
    void findActive() {
        List<ItemDTO> testList = newArrayList(firstItem().build());
        when(itemService.findByState(ACTIVE)).thenReturn(testList);
        ResponseEntity<List<ItemDTO>> findActive = itemController.findActive();
        List<ItemDTO> all = findActive.getBody();
        assertNotNull(all);
        assertArrayEquals(testList.toArray(), all.toArray());
        HttpStatus statusCode = findActive.getStatusCode();
        assertEquals(OK, statusCode);
    }

    @Test
    void findDiscontinued() {
        List<ItemDTO> testList = newArrayList(secondItem().build());
        when(itemService.findByState(DISCONTINUED)).thenReturn(testList);
        ResponseEntity<List<ItemDTO>> findDiscontinued = itemController.findDiscontinued();
        List<ItemDTO> all = findDiscontinued.getBody();
        assertNotNull(all);
        assertArrayEquals(testList.toArray(), all.toArray());
        HttpStatus statusCode = findDiscontinued.getStatusCode();
        assertEquals(OK, statusCode);
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
        ItemDTO emptyCodeItem = firstItem().withItemCode(null).build();
        ItemDTO emptyDescriptionItem = firstItem().withDescription(null).build();
        ItemDTO invalidDescriptionItem = firstItem().withDescription("").build();
        assertThrows(ItemEmptyException.class,
                () -> itemController.createItem(null));
        assertThrows(ItemEmptyCodeException.class,
                () -> itemController.createItem(emptyCodeItem));
        assertThrows(ItemEmptyDescriptionException.class,
                () -> itemController.createItem(emptyDescriptionItem));
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
        ItemDTO newItem = newItem().withState(null).build();
        ArgumentCaptor<ItemDTO> argumentCaptor = ArgumentCaptor.forClass(ItemDTO.class);
        HttpStatus statusCode = itemController.createItem(newItem).getStatusCode();
        verify(itemService, times(1)).saveItem(argumentCaptor.capture());
        assertEquals(CREATED, statusCode);
        ItemDTO savedItem = argumentCaptor.getValue();
        Date itemCreationDate = savedItem.getCreationDate();
        assertEquals(ACTIVE.getName(), savedItem.getState());
        LocalDate currentDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate creationDate = itemCreationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        assertEquals(currentDate, creationDate);
    }

    @Test
    void editInvalidItem() {
        ItemDTO nullCodeItem = firstItem().withItemCode(null).build();
        ItemDTO nullDescriptionItem = firstItem().withDescription(null).build();
        ItemDTO invalidDescriptionItem = firstItem().withDescription("").build();
        ItemDTO discontinuedItem = firstItem().withState(DISCONTINUED.getName()).build();
        ItemDTO firstItem = firstItem().build();
        long firstItemCode = firstItem.getItemCode();
        long invalidItemCode = firstItemCode + 10;
        assertThrows(ItemEmptyException.class,
                () -> itemController.editItem(null, firstItemCode));
        assertThrows(ItemEmptyCodeException.class,
                () -> itemController.editItem(nullCodeItem, firstItemCode));
        assertThrows(ItemEmptyDescriptionException.class,
                () -> itemController.editItem(nullDescriptionItem, firstItemCode));
        assertThrows(ItemEmptyDescriptionException.class,
                () -> itemController.editItem(invalidDescriptionItem, firstItemCode));
        assertThrows(ItemInvalidStateException.class,
                () -> itemController.editItem(discontinuedItem, firstItemCode));
        assertThrows(ItemInvalidCodeException.class,
                () -> itemController.editItem(firstItem, invalidItemCode));
    }

    @Test
    void editNonexistentItem() {
        ItemDTO item = firstItem().build();
        long itemCode = item.getItemCode();
        when(itemService.findByItemCode(itemCode)).thenReturn(null);
        assertThrows(ItemNotFoundException.class,
                () -> itemController.editItem(item, itemCode));
    }

    @Test
    void editValidItem() {
        ItemDTO firstItem = firstItem().build();
        ItemDTO item = firstItem().withDescription("editedDescription").withCreator(null).withCreationDate(null).build();
        when(itemService.findByItemCode(item.getItemCode())).thenReturn(firstItem);
        ArgumentCaptor<ItemDTO> argumentCaptor = ArgumentCaptor.forClass(ItemDTO.class);
        HttpStatus statusCode = itemController.editItem(item, item.getItemCode()).getStatusCode();
        assertEquals(OK, statusCode);
        verify(itemService, times(1)).saveItem(argumentCaptor.capture());
        ItemDTO savedItem = argumentCaptor.getValue();
        assertEquals("editedDescription", savedItem.getDescription());
        assertEquals(firstItem.getCreationDate(), savedItem.getCreationDate());
        assertEquals(firstItem.getCreator(), savedItem.getCreator());
    }

    @Test
    void deactivateItemWithInvalidItemDeactivator() {
        ItemDeactivatorDTO emptyItemIdItemDeactivator = firstItemDeactivator().withItemId(null).build();
        ItemDeactivatorDTO emptyUserIdItemDeactivator = firstItemDeactivator().withUserId(null).build();
        ItemDeactivatorDTO emptyReasonItemDeactivator = firstItemDeactivator().withReason(null).build();
        ItemDeactivatorDTO invalidReasonItemDeactivator = firstItemDeactivator().withReason("").build();
        assertThrows(ItemDeactivatorEmptyException.class,
                () -> itemController.deactivateItem(null));
        assertThrows(ItemDeactivatorEmptyItemIdException.class,
                () -> itemController.deactivateItem(emptyItemIdItemDeactivator));
        assertThrows(ItemDeactivatorEmptyUserIdException.class,
                () -> itemController.deactivateItem(emptyUserIdItemDeactivator));
        assertThrows(ItemDeactivatorEmptyReasonException.class,
                () -> itemController.deactivateItem(emptyReasonItemDeactivator));
        assertThrows(ItemDeactivatorEmptyReasonException.class,
                () -> itemController.deactivateItem(invalidReasonItemDeactivator));
    }

    @Test
    void deactivateNonexistentItemAndNonexistentUser() {
        ItemDeactivatorDTO invalidItemIdItemDeactivator = firstItemDeactivator().withItemId(999L).build();
        assertThrows(ItemNotFoundException.class,
                () -> itemController.deactivateItem(invalidItemIdItemDeactivator));
        ItemDeactivatorDTO invalidUserIdItemDeactivator = firstItemDeactivator().withUserId(999L).build();
        when(itemService.findById(invalidUserIdItemDeactivator.getItemId())).thenReturn(firstItem().build());
        assertThrows(UserNotFoundException.class,
                () -> itemController.deactivateItem(invalidUserIdItemDeactivator));
    }

    @Test
    void deactivateDiscontinuedItem() {
        ItemDeactivatorDTO itemDeactivator = firstItemDeactivator().build();
        when(itemService.findById(itemDeactivator.getItemId())).thenReturn(secondItem().build());
        assertThrows(ItemAlreadyDiscontinuedException.class,
                () -> itemController.deactivateItem(itemDeactivator));
    }

    @Test
    void deactivateValidItem() {
        ItemDeactivatorDTO itemDeactivator = firstItemDeactivator().build();
        when(itemService.findById(itemDeactivator.getItemId())).thenReturn(firstItem().build());
        when(userService.findById(itemDeactivator.getUserId())).thenReturn(firstUser().build());
        HttpStatus statusCode = itemController.deactivateItem(itemDeactivator).getStatusCode();
        assertEquals(OK, statusCode);
    }

    @Test
    void deleteNonexistentItem() {
        when(itemService.findByItemCode(1)).thenReturn(null);
        assertThrows(ItemNotFoundException.class,
                () -> itemController.deleteByItemCode(1));
    }

    @Test
    void deleteValidItem() {
        ItemDTO firstItem = firstItem().build();
        Long firstItemCode = firstItem.getItemCode();
        when(itemService.findByItemCode(firstItemCode)).thenReturn(firstItem);
        HttpStatus statusCode = itemController.deleteByItemCode(firstItemCode)
                .getStatusCode();
        assertEquals(OK, statusCode);
    }
}