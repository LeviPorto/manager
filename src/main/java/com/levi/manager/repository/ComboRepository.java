package com.levi.manager.repository;

import com.levi.manager.crud.AbstractCrudRepository;
import com.levi.manager.domain.Combo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComboRepository extends AbstractCrudRepository<Combo> {

    List<Combo> findByRestaurantId(Integer restaurantId);

}
