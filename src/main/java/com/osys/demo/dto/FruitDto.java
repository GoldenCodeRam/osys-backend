package com.osys.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FruitDto(
        @NotNull(message = "Stock is required")
        @Min(message = "The stock can't be negative ", value = 0)
        Integer stock,
        @NotBlank(message = "Type is required")
        String type,
        @NotNull(message = "Value is required")
        @Min(message = "The value can't be negative", value = 0)
        Float value
) {
}
