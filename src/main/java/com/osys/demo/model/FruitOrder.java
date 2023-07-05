package com.osys.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Iterator;

@Entity
public class FruitOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "fruit_order_id_seq")
    public Integer id;
    @JdbcTypeCode(SqlTypes.JSON)
    public String fruitList;
    public Float totalValue;
    public LocalDate createdAt = LocalDate.now();
    public LocalDate updatedAt = LocalDate.now();

    public FruitOrder() {
    }

    public FruitOrder(String fruitList, Float totalValue) {
        this.fruitList = fruitList;
        this.totalValue = totalValue;
    }
}
