package com.osys.demo.controller;

import com.osys.demo.dto.FruitDto;
import com.osys.demo.model.Fruit;
import com.osys.demo.service.FruitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class FruitController {
    @Autowired
    private FruitService fruitService;

    @GetMapping("/fruits/find")
    public ResponseEntity<Fruit> findOne(@RequestParam Long id) {
        Optional<Fruit> result = this.fruitService.findOne(id);
        return result
                .map(fruit -> new ResponseEntity<>(fruit, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/fruits")
    public List<Fruit> findAll() {
        return this.fruitService.findAll();
    }

    @GetMapping("/fruits/paginate")
    public Page<Fruit> findAll(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return this.fruitService.findAll(page, pageSize);
    }

    @PostMapping("/fruits/create")
    public ResponseEntity<Fruit> create(@Valid @RequestBody FruitDto fruitDto) {
        return new ResponseEntity<>(
                this.fruitService.create(fruitDto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/fruits/update")
    public ResponseEntity<Fruit> update(
            @Valid @RequestBody FruitDto fruitDto,
            @RequestParam Long id
    ) {
        Optional<Fruit> result = this.fruitService.updateOne(id, fruitDto);
        return result
                .map(fruit -> new ResponseEntity<>(fruit, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/fruits/delete")
    public ResponseEntity<Fruit> delete(@RequestParam Long id) {
        Optional<Fruit> result = this.fruitService.deleteOne(id);
        return result
                .map(fruit -> new ResponseEntity<>(fruit, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
