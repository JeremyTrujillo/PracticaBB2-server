package com.example.practicabitboxer2.controller;

import com.example.practicabitboxer2.dtos.ItemDTO;
import com.example.practicabitboxer2.exceptions.*;
import com.example.practicabitboxer2.model.ItemState;
import com.example.practicabitboxer2.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.example.practicabitboxer2.model.ItemState.ACTIVE;
import static com.example.practicabitboxer2.model.ItemState.INACTIVE;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    ItemService service;

    @GetMapping(value = "/{code}")
    public ResponseEntity<ItemDTO> findByItemCode(@PathVariable long code) {
        ItemDTO byItemCode = service.findByItemCode(code);
        if (byItemCode == null) {
            throw new ItemNotFoundException();
        }
        return new ResponseEntity<>(byItemCode, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ItemDTO>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/active")
    public ResponseEntity<List<ItemDTO>> findActive() {
        return this.findByState(ACTIVE);
    }

    @GetMapping(value = "/inactive")
    public ResponseEntity<List<ItemDTO>> findInactive() {
        return this.findByState(INACTIVE);
    }

    @PostMapping
    public ResponseEntity<Void> createItem(@RequestBody ItemDTO item) {
        checkItemConstraints(item);
        ItemDTO itemByCode = service.findByItemCode(item.getItemCode());
        if (itemByCode != null) {
            throw new ItemCodeAlreadyExistsException();
        }
        item.setState(ACTIVE.getName());
        item.setCreationDate(new Date());
        service.saveItem(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> editItem(@RequestBody ItemDTO item, @RequestParam long itemCode) {
        checkItemConstraints(item);
        if (!item.getItemCode().equals(itemCode)) {
            throw new ItemInvalidCodeException();
        }
        ItemDTO itemByCode = service.findByItemCode(item.getItemCode());
        if (itemByCode == null) {
            throw new ItemNotFoundException();
        }
        if (!"ACTIVE".equals(itemByCode.getState())) {
            throw new ItemInvalidStateException();
        }
        service.saveItem(item);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<List<ItemDTO>> findByState(ItemState state) {
        return new ResponseEntity<>(this.service.findByState(state), HttpStatus.OK);
    }

    private void checkItemConstraints(ItemDTO item) {
        if (item == null) {
            throw new ItemEmptyException();
        }
        if (item.getItemCode() == null) {
            throw new ItemEmptyCodeException();
        }
        if (item.getDescription() == null) {
            throw new ItemEmptyDescriptionException();
        }

    }
}
