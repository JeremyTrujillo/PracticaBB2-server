package com.example.practicabitboxer2.controller;

import com.example.practicabitboxer2.dtos.ItemDTO;
import com.example.practicabitboxer2.exceptions.ItemEmptyException;
import com.example.practicabitboxer2.exceptions.ItemInvalidCodeException;
import com.example.practicabitboxer2.exceptions.ItemInvalidDescriptionException;
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
        return new ResponseEntity<>(service.findByItemCode(code), HttpStatus.OK);
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
    public ResponseEntity<Void> saveItem(@RequestBody ItemDTO item) {
        if (item == null) {
            throw new ItemEmptyException();
        }
        if (item.getItemCode() == null) {
            throw new ItemInvalidCodeException();
        }
        if (item.getDescription() == null) {
            throw new ItemInvalidDescriptionException();
        }
        item.setState(ACTIVE.getName());
        item.setCreationDate(new Date());
        service.saveItem(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<List<ItemDTO>> findByState(ItemState state) {
        return new ResponseEntity<>(this.service.findByState(state), HttpStatus.OK);
    }
}
