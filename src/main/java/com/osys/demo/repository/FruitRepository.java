package com.osys.demo.repository;

import com.osys.demo.model.Fruit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FruitRepository extends
        CrudRepository<Fruit, Long>,
        PagingAndSortingRepository<Fruit, Long> {
    List<Fruit> findByType(String type);
}
