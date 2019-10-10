package com.levi.manager.repository;

import com.levi.manager.crud.AbstractCrudRepository;
import com.levi.manager.domain.Food;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends AbstractCrudRepository<Food> {

    List<Food> findByRestaurantId(Integer id);


}
