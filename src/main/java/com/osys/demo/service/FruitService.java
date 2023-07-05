package com.osys.demo.service;

import com.osys.demo.dto.FruitDto;
import com.osys.demo.model.Fruit;
import com.osys.demo.repository.FruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FruitService {
    private static final Integer DEFAULT_PAGE_SIZE = 20;
    private static final Integer DEFAULT_PAGE = 0;

    @Autowired
    private FruitRepository fruitRepository;

    public Fruit create(FruitDto fruitDto) {
        return this.fruitRepository.save(
                new Fruit(fruitDto.stock(), fruitDto.type(), fruitDto.value())
        );
    }

    public Optional<Fruit> findOne(Long id) {
        return this.fruitRepository.findById(id);
    }

    public List<Fruit> findAll() {
        List<Fruit> result = new ArrayList<>();
        this.fruitRepository.findAll().forEach(result::add);
        return result;
    }

    public Page<Fruit> findAll(Integer page, Integer pageSize) {
        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        if (page <= 0) {
            page = DEFAULT_PAGE;
        }

        return this.fruitRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Optional<Fruit> updateOne(Long id, FruitDto fruitDto) {
        Optional<Fruit> result = this.fruitRepository.findById(id);
        return result.map(fruit -> {
            fruit.stock = fruitDto.stock();
            fruit.type = fruitDto.type();
            fruit.value = fruitDto.value();
            fruit.updatedAt = LocalDate.now();
            return Optional.of(this.fruitRepository.save(fruit));
        }).orElse(Optional.empty());
    }

    public Optional<Fruit> deleteOne(Long id) {
        Optional<Fruit> result = this.fruitRepository.findById(id);
        return result.map(fruit -> {
            this.fruitRepository.delete(fruit);
            return Optional.of(fruit);
        }).orElse(Optional.empty());
    }
}
