package com.levi.manager.listener;

import com.levi.manager.dto.EvaluatedRestaurantDTO;
import com.levi.manager.domain.Restaurant;
import com.levi.manager.service.RestaurantService;
import org.springframework.stereotype.Component;

@Component
public class RatingValueListener implements UpdateRatingListener {

    private final RestaurantService restaurantService;

    public RatingValueListener(final RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Override
    public void ratingWasUpdated(EvaluatedRestaurantDTO evaluatedRestaurantDTO, Restaurant restaurant) {
        restaurant.setRating(evaluatedRestaurantDTO.getRating());
        restaurantService.create(restaurant);
    }

}
