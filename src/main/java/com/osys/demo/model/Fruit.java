package com.osys.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Optional;

@Entity
public class Fruit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "fruit_id_seq")
    public Integer id;
    public Integer stock;
    public String type;
    public Float value;
    public LocalDate createdAt = LocalDate.now();
    public LocalDate updatedAt = LocalDate.now();

    public Fruit() {

    }

    public Fruit(Integer stock, String type, Float value) {
        this.stock = stock;
        this.type = type;
        this.value = value;
    }
}
