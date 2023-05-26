package com.example.practicabitboxer2.repositories;

import com.example.practicabitboxer2.model.Item;
import com.example.practicabitboxer2.model.ItemState;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @EntityGraph(value = "ItemWithSuppliers")
    Optional<Item> findOneWithSuppliersByItemCode(long itemCode);

    @EntityGraph(value = "ItemWithPriceReductions")
    Optional<Item> findOneWithPriceReductionsByItemCode(long itemCode);

    List<Item> findByState(ItemState state);

    List<Item> findCheapestPerSupplier();

    @Transactional
    @Modifying
    void deleteByItemCode(long itemCode);
}
