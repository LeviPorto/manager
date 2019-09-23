package com.levi.manager.listener;

import com.levi.manager.dto.AvaliatedRestaurantDTO;
import com.levi.manager.entity.Restaurant;
import com.levi.manager.service.RestaurantService;
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
