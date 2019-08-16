package com.levi.manager.repositories;

import com.levi.manager.entities.Restaurant;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {
}
