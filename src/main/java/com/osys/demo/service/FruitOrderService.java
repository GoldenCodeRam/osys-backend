package com.osys.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osys.demo.dto.FruitDto;
import com.osys.demo.dto.FruitOrderDto;
import com.osys.demo.model.Fruit;
import com.osys.demo.model.FruitOrder;
import com.osys.demo.model.FruitOrderItem;
import com.osys.demo.repository.FruitOrderRepository;
import com.osys.demo.repository.FruitRepository;
import com.osys.demo.service.error.StockNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Service
public class FruitOrderService {
    private static final Integer DEFAULT_PAGE_SIZE = 20;
    private static final Integer DEFAULT_PAGE = 0;
    @Autowired
    private FruitOrderRepository fruitOrderRepository;
    @Autowired
    private FruitRepository fruitRepository;

    @Transactional(rollbackFor = Exception.class)
    public FruitOrder create(FruitOrderDto fruitOrderDto) throws JsonProcessingException, StockNotAvailableException {
        // TODO: Maybe split this into multiple pieces?

        FruitOrderItem[] items = fruitOrderDto.fruitList();
        float totalValue = 0f;

        for (FruitOrderItem item : items) {
            List<Fruit> fruits = this.fruitRepository.findByType(item.type);
            if (fruits.size() > 0) {
                // As the fruit type is unique
                assert fruits.size() == 1;

                Fruit fruit = fruits.get(0);
                float fruitsValue = 0f;

                // We don't have that much fruit!
                if (fruit.stock < item.amount) {
                    throw new StockNotAvailableException();
                }

                fruitsValue += fruit.value * item.amount;
                // More than 10 fruits have been purchased
                if (item.amount > 10) {
                    // 5% discount over the total value of the fruits
                    fruitsValue -= (fruit.value * item.amount) * 0.05;
                }

                item.value = fruitsValue;
                totalValue += fruitsValue;
                fruit.stock -= item.amount;

                // Update fruit stock
                this.fruitRepository.save(fruit);
            }
        }

        Set<String> hashSet = new HashSet<>();
        Arrays.stream(items).forEach(item -> hashSet.add(item.type));
        // More than 5 fruits have been purchased
        if (hashSet.size() > 5) {
            // 10% discount over the total value
            totalValue -= totalValue * 0.1;
        }

        String json = new ObjectMapper().writeValueAsString(items);
        return this.fruitOrderRepository.save(new FruitOrder(json, totalValue));
    }

    public Optional<FruitOrder> findOne(Long id) {
        return this.fruitOrderRepository.findById(id);
    }

    public List<FruitOrder> findAll() {
        List<FruitOrder> result = new ArrayList<>();
        this.fruitOrderRepository.findAll().forEach(result::add);
        return result;
    }

    public Page<FruitOrder> findAll(Integer page, Integer pageSize) {
        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        if (page <= 0) {
            page = DEFAULT_PAGE;
        }

        return this.fruitOrderRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Optional<FruitOrder> deleteOne(Long id) {
        Optional<FruitOrder> result = this.fruitOrderRepository.findById(id);
        return result.map(fruitOrder -> {
            this.fruitOrderRepository.delete(fruitOrder);
            return Optional.of(fruitOrder);
        }).orElse(Optional.empty());
    }
}
