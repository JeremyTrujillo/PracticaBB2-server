package com.example.practicabitboxer2.repositories;

import com.example.practicabitboxer2.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "SELECT * FROM ITEMS WHERE itemCode = :itemCode", nativeQuery = true)
    Optional<Item> findByItemCode(Long itemCode);

    @Query(value = "SELECT * FROM items WHERE state = :state", nativeQuery = true)
    List<Item> findByState(String state);
}
