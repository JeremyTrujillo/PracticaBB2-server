package com.example.practicabitboxer2.utils.builders;

import com.example.practicabitboxer2.dtos.ItemDTO;
import com.example.practicabitboxer2.dtos.PriceReductionDTO;
import com.example.practicabitboxer2.dtos.SupplierDTO;
import com.example.practicabitboxer2.dtos.UserDTO;

import java.util.Date;
import java.util.List;

public class ItemBuilder {

    private Long id;
    private Long itemCode;
    private String description;
    private Float price;
    private String state;
    private List<SupplierDTO> suppliers;
    private List<PriceReductionDTO> priceReductions;
    private Date creationDate;
    private UserDTO creator;

    public static ItemBuilder itemBuilder() {
        return new ItemBuilder();
    }

    public ItemBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ItemBuilder withItemCode(Long itemCode) {
        this.itemCode = itemCode;
        return this;
    }

    public ItemBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ItemBuilder withPrice(Float price) {
        this.price = price;
        return this;
    }

    public ItemBuilder withState(String state) {
        this.state = state;
        return this;
    }

    public ItemBuilder withSuppliers(List<SupplierDTO> suppliers) {
        this.suppliers = suppliers;
        return this;
    }

    public ItemBuilder withPriceReductions(List<PriceReductionDTO> priceReductions) {
        this.priceReductions = priceReductions;
        return this;
    }

    public ItemBuilder withCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public ItemBuilder withCreator(UserDTO creator) {
        this.creator = creator;
        return this;
    }

    public ItemDTO build() {
        ItemDTO item = new ItemDTO();
        item.setId(this.id);
        item.setItemCode(this.itemCode);
        item.setDescription(this.description);
        item.setPrice(this.price);
        item.setState(this.state);
        item.setCreationDate(this.creationDate);
        item.setSuppliers(this.suppliers);
        item.setPriceReductions(this.priceReductions);
        item.setCreator(this.creator);
        return item;
    }
}
