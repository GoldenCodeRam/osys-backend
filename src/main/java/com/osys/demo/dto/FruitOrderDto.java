package com.osys.demo.dto;

import com.osys.demo.model.FruitOrderItem;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

public record FruitOrderDto(
    @NotNull(message = "Fruit list is required")
    FruitOrderItem[] fruitList
) {
}
