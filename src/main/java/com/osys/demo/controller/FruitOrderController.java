package com.osys.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.osys.demo.dto.FruitDto;
import com.osys.demo.dto.FruitOrderDto;
import com.osys.demo.model.Fruit;
import com.osys.demo.model.FruitOrder;
import com.osys.demo.service.FruitOrderService;
import com.osys.demo.service.error.StockNotAvailableException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class FruitOrderController {
    @Autowired
    FruitOrderService fruitOrderService;

    @PostMapping("fruit-orders/create")
    public ResponseEntity<Object> create(@Valid @RequestBody FruitOrderDto fruitOrderDto) {
        try {
            return new ResponseEntity<>(
                    this.fruitOrderService.create(fruitOrderDto), HttpStatus.CREATED
            );
        } catch (StockNotAvailableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/fruit-orders/find")
    public ResponseEntity<FruitOrder> findOne(@RequestParam Long id) {
        Optional<FruitOrder> result = this.fruitOrderService.findOne(id);
        return result
                .map(fruitOrder -> new ResponseEntity<>(fruitOrder, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/fruit-orders")
    public List<FruitOrder> findAll() {
        return this.fruitOrderService.findAll();
    }

    @GetMapping("/fruit-orders/paginate")
    public Page<FruitOrder> findAll(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return this.fruitOrderService.findAll(page, pageSize);
    }

    @DeleteMapping("/fruit-orders/delete")
    public ResponseEntity<FruitOrder> delete(@RequestParam Long id) {
        Optional<FruitOrder> result = this.fruitOrderService.deleteOne(id);
        return result
                .map(fruitOrder -> new ResponseEntity<>(fruitOrder, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
