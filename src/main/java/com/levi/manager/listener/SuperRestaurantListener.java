package com.levi.manager.listener;

import com.levi.manager.dto.EvaluatedRestaurantDTO;
import com.levi.manager.domain.Restaurant;
import com.levi.manager.service.RestaurantService;
import org.springframework.stereotype.Component;

@Component
public class SuperRestaurantListener implements UpdateRatingListener {

    private final RestaurantService restaurantService;

    public SuperRestaurantListener(final RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Override
    public void ratingWasUpdated(EvaluatedRestaurantDTO evaluatedRestaurantDTO, Restaurant restaurant) {
        restaurant.setIsSuperRestaurant(evaluatedRestaurantDTO.isSuperRestaurant());
        restaurantService.create(restaurant);
    }

}
