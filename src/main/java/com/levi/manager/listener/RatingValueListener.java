package com.levi.manager.listener;

import com.levi.manager.dtos.AvaliatedRestaurantDTO;
import com.levi.manager.entities.Restaurant;
import com.levi.manager.services.RestaurantService;
import org.springframework.stereotype.Component;

@Component
public class RatingValueListener implements UpdateRatingListener {

    private final RestaurantService restaurantService;

    public RatingValueListener(final RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Override
    public void ratingWasUpdated(AvaliatedRestaurantDTO avaliatedRestaurantDTO, Restaurant restaurant) {
        restaurant.setRating(avaliatedRestaurantDTO.getRating());
        restaurantService.create(restaurant);
    }

}
