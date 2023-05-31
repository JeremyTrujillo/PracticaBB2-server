package com.example.practicabitboxer2.controller;

import com.example.practicabitboxer2.dtos.SupplierDTO;
import com.example.practicabitboxer2.services.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
public class SupplierController {

    private final @NonNull SupplierService service;

    @GetMapping("/withReducedItems")
    public ResponseEntity<List<SupplierDTO>> findWithReducedItems() {
        return new ResponseEntity<>(service.findWithReducedItems(), OK);
    }
}
