package com.levi.manager.repository;

import com.levi.manager.crud.AbstractCrudRepository;
import com.levi.manager.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends AbstractCrudRepository<Restaurant>, JpaSpecificationExecutor<Restaurant> {

    Restaurant findByDeliveryManId(Integer deliveryManId);

    List<Restaurant> findByIdIn(List<Integer> ids);

}
