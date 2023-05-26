package com.example.practicabitboxer2.controller;

import com.example.practicabitboxer2.dtos.*;
import com.example.practicabitboxer2.exceptions.*;
import com.example.practicabitboxer2.model.ItemState;
import com.example.practicabitboxer2.services.ItemService;
import com.example.practicabitboxer2.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.practicabitboxer2.model.ItemState.ACTIVE;
import static com.example.practicabitboxer2.model.ItemState.DISCONTINUED;
import static java.lang.Math.max;
import static java.lang.Math.min;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
public class ItemController {

    private final @NonNull ItemService itemService;
    private final @NonNull UserService userService;

    @GetMapping(value = "/{code}")
    public ResponseEntity<ItemDTO> findByItemCode(@PathVariable long code) {
        ItemDTO byItemCode = itemService.findByItemCode(code);
        if (byItemCode == null) {
            throw new ItemNotFoundException();
        }
        return new ResponseEntity<>(byItemCode, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ItemDTO>> findAll() {
        return new ResponseEntity<>(itemService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/active")
    public ResponseEntity<List<ItemDTO>> findActive() {
        return this.findByState(ACTIVE);
    }

    @GetMapping(value = "/discontinued")
    public ResponseEntity<List<ItemDTO>> findDiscontinued() {
        return this.findByState(DISCONTINUED);
    }

    @GetMapping(value = "/cheapestPerSupplier")
    public ResponseEntity<List<ItemDTO>> findCheapestPerSupplier() {
        return new ResponseEntity<>(itemService.findCheapestPerSupplier(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createItem(@RequestBody ItemDTO item) {
        checkItemConstraints(item);
        ItemDTO itemByCode = itemService.findByItemCode(item.getItemCode());
        if (itemByCode != null) {
            throw new ItemCodeAlreadyExistsException();
        }
        item.setState(ACTIVE.getName());
        item.setCreationDate(new Date());
        itemService.saveItem(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> editItem(@RequestBody ItemDTO item, @RequestParam long id) {
        checkItemConstraints(item);
        if (item.getId() == null) {
            throw new ItemEmptyIdException();
        }
        checkSupplierConstraints(item.getSuppliers());
        checkPriceReductionsConstraints(item.getPriceReductions());
        if (!item.getId().equals(id)) {
            throw new ItemInvalidIdException();
        }
        ItemDTO itemById = itemService.findById(id);
        if (itemById == null) {
            throw new ItemNotFoundException();
        }
        if (!item.getItemCode().equals(itemById.getItemCode())) {
            throw new ItemInvalidCodeException();
        }
        if (!"ACTIVE".equals(item.getState()) || (!"ACTIVE".equals(itemById.getState()))) {
            throw new ItemInvalidStateException();
        }
        item.setCreationDate(itemById.getCreationDate());
        item.setCreator(itemById.getCreator());
        itemService.saveItem(item);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/deactivate")
    public ResponseEntity<Void> deactivateItem(@RequestBody ItemDeactivatorDTO itemDeactivator) {
        checkItemDeactivatorConstraints(itemDeactivator);
        ItemDTO item = itemService.findById(itemDeactivator.getItemId());
        if (item == null) {
            throw new ItemNotFoundException();
        }
        if (!ACTIVE.getName().equals(item.getState())) {
            throw new ItemAlreadyDiscontinuedException();
        }
        UserDTO user = userService.findById(itemDeactivator.getUserId());
        if (user == null) {
            throw new UserNotFoundException();
        }
        itemService.deactivateItem(item, user, itemDeactivator.getReason());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping
    public ResponseEntity<Void> deleteByItemCode(@RequestParam long itemCode) {
        ItemDTO item = itemService.findByItemCode(itemCode);
        if (item == null) {
            throw new ItemNotFoundException();
        }
        itemService.deleteByItemCode(itemCode);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<List<ItemDTO>> findByState(ItemState state) {
        return new ResponseEntity<>(this.itemService.findByState(state), HttpStatus.OK);
    }

    private void checkItemConstraints(ItemDTO item) {
        if (item == null) {
            throw new ItemEmptyException();
        }
        if (item.getItemCode() == null) {
            throw new ItemEmptyCodeException();
        }
        if (!StringUtils.hasText(item.getDescription())) {
            throw new ItemEmptyDescriptionException();
        }
    }

    private void checkSupplierConstraints(List<SupplierDTO> suppliers) {
        if (suppliers == null) {
            throw new ItemEmptySuppliersException();
        }
        Set<SupplierDTO> suppliersSet = new HashSet<>(suppliers);
        if (suppliersSet.size() != suppliers.size()) {
            throw new ItemSupplierDuplicatedException();
        }
    }

    private void checkPriceReductionsConstraints(List<PriceReductionDTO> priceReductions) {
        if (priceReductions == null) {
            throw new ItemEmptyPriceReductionsException();
        }
        priceReductions.sort(PriceReductionDTO::compareTo);
        for (int i = 0; i < priceReductions.size() - 1; i++) {
            PriceReductionDTO first = priceReductions.get(i);
            PriceReductionDTO second = priceReductions.get(i+1);
            if (max(first.getStartDate().getTime(), second.getStartDate().getTime())
                    <= min(first.getEndDate().getTime(), second.getEndDate().getTime())) {
                throw new OverlappingPriceReductionsException();
            }
        }
    }

    private void checkItemDeactivatorConstraints(ItemDeactivatorDTO itemDeactivator) {
        if (itemDeactivator == null) {
            throw new ItemDeactivatorEmptyException();
        }
        if (itemDeactivator.getItemId() == null) {
            throw new ItemDeactivatorEmptyItemIdException();
        }
        if (itemDeactivator.getUserId() == null) {
            throw new ItemDeactivatorEmptyUserIdException();
        }
        if (!StringUtils.hasText(itemDeactivator.getReason())) {
            throw new ItemDeactivatorEmptyReasonException();
        }
    }
}
