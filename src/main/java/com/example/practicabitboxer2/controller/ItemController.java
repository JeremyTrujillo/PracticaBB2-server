package com.example.practicabitboxer2.controller;

import com.example.practicabitboxer2.dtos.ItemDTO;
import com.example.practicabitboxer2.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    ItemService service;

    @GetMapping()
    public List<ItemDTO> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public ItemDTO findById(@PathVariable long id) {
        return service.findById(id);
    }

}
