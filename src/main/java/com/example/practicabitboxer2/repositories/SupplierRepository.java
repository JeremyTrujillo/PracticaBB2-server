package com.example.practicabitboxer2.repositories;

import com.example.practicabitboxer2.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    List<Supplier> findWithReducedItems();
}
