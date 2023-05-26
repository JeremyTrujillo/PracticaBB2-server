package com.example.practicabitboxer2.services;

import com.example.practicabitboxer2.dtos.SupplierDTO;
import com.example.practicabitboxer2.model.Supplier;
import com.example.practicabitboxer2.repositories.SupplierRepository;
import com.example.practicabitboxer2.utils.SupplierUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SupplierService {

    private final @NonNull SupplierRepository repository;

    public List<SupplierDTO> findWithReducedItems() {
        List<Supplier> suppliers = repository.findWithReducedItems();
        return SupplierUtils.entitiesToDtos(suppliers);
    }
}
