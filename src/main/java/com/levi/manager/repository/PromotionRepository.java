package com.levi.manager.repository;

import com.levi.manager.crud.AbstractCrudRepository;
import com.levi.manager.domain.Promotion;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends AbstractCrudRepository<Promotion> {

    List<Promotion> findByRestaurantId(Integer id);


}
