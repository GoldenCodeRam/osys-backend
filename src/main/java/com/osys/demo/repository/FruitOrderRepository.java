package com.osys.demo.repository;

import com.osys.demo.model.FruitOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FruitOrderRepository extends
        CrudRepository<FruitOrder, Long>,
        PagingAndSortingRepository<FruitOrder, Long> {
}
