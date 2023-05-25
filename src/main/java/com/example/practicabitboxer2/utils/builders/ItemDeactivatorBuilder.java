package com.example.practicabitboxer2.utils.builders;

import com.example.practicabitboxer2.dtos.ItemDeactivatorDTO;

public class ItemDeactivatorBuilder {

    private Long itemId;
    private Long userId;
    private String reason;

    public static ItemDeactivatorBuilder itemDeactivatorBuilder() {
        return new ItemDeactivatorBuilder();
    }

    public ItemDeactivatorBuilder withItemId(Long itemId) {
        this.itemId = itemId;
        return this;
    }

    public ItemDeactivatorBuilder withUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public ItemDeactivatorBuilder withReason(String reason) {
        this.reason = reason;
        return this;
    }

    public ItemDeactivatorDTO build() {
        ItemDeactivatorDTO itemDeactivator = new ItemDeactivatorDTO();
        itemDeactivator.setItemId(this.itemId);
        itemDeactivator.setUserId(this.userId);
        itemDeactivator.setReason(this.reason);
        return itemDeactivator;
    }
}
