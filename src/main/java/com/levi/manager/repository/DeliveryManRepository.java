package com.levi.manager.repository;

import com.levi.manager.crud.AbstractCrudRepository;
import com.levi.manager.domain.DeliveryMan;
import com.levi.manager.domain.enumeration.Occupation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryManRepository extends AbstractCrudRepository<DeliveryMan> {

    DeliveryMan findTop1ByRestaurantIdAndOccupation(Integer restaurantId, Occupation occupation);

    List<DeliveryMan> findByRestaurantIdAndOccupation(Integer restaurantId, Occupation occupation);

}
