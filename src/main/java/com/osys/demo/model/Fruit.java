package com.osys.demo.model;

import java.time.LocalDate;

public record Fruit(
        int id,
        int stock,
        float value,
        LocalDate createdAt,
        LocalDate updatedAt
) {
}
