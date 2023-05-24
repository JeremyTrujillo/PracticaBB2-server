package com.example.practicabitboxer2.controller;

import com.example.practicabitboxer2.dtos.ItemDTO;
import com.example.practicabitboxer2.dtos.SupplierDTO;
import com.example.practicabitboxer2.exceptions.*;
import com.example.practicabitboxer2.model.ItemState;
import com.example.practicabitboxer2.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.practicabitboxer2.model.ItemState.ACTIVE;
import static com.example.practicabitboxer2.model.ItemState.DISCONTINUED;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemController {

    private final @NonNull ItemService service;

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

    @GetMapping(value = "/discontinued")
    public ResponseEntity<List<ItemDTO>> findDiscontinued() {
        return this.findByState(DISCONTINUED);
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
        checkSupplierConstraints(item.getSuppliers());
        if (!item.getItemCode().equals(itemCode)) {
            throw new ItemInvalidCodeException();
        }
        ItemDTO itemByCode = service.findByItemCode(item.getItemCode());
        if (itemByCode == null) {
            throw new ItemNotFoundException();
        }
        if (!"ACTIVE".equals(item.getState()) || !"ACTIVE".equals(itemByCode.getState())) {
            throw new ItemInvalidStateException();
        }
        item.setCreationDate(itemByCode.getCreationDate());
        item.setCreator(itemByCode.getCreator());
        service.saveItem(item);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping
    public ResponseEntity<Void> deleteByItemCode(@RequestParam long itemCode) {
        ItemDTO item = service.findByItemCode(itemCode);
        if (item == null) {
            throw new ItemNotFoundException();
        }
        service.deleteByItemCode(itemCode);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<List<ItemDTO>> findByState(ItemState state) {
        return new ResponseEntity<>(this.service.findByState(state), HttpStatus.OK);
    }

    @PutMapping("/deactivate")
    public ResponseEntity<Void> editItem(@RequestParam long itemCode) {
        ItemDTO item = service.findByItemCode(itemCode);
        if (item == null) {
            throw new ItemNotFoundException();
        }
        if (!ACTIVE.getName().equals(item.getState())) {
            throw new ItemAlreadyDiscontinuedException();
        }
        service.deactivateItem(item);
        return new ResponseEntity<>(HttpStatus.OK);
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

    private void checkSupplierConstraints(List<SupplierDTO> newItemSuppliers) {
        Set<SupplierDTO> suppliersSet = new HashSet<>(newItemSuppliers);
        if (suppliersSet.size() != newItemSuppliers.size()) {
            throw new ItemSupplierDuplicatedException();
        }
    }
}
