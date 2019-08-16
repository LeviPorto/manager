package com.levi.manager.listener;

import com.levi.manager.dtos.AvaliatedRestaurantDTO;
import com.levi.manager.entities.Restaurant;
import com.levi.manager.services.RestaurantService;

public class RateValueListener implements UpdateRateListener {

    private final RestaurantService restaurantService;

    public RateValueListener(final RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Override
    public void rateWasUpdated(AvaliatedRestaurantDTO avaliatedRestaurantDTO, Restaurant restaurant) {
        restaurant.setRate(avaliatedRestaurantDTO.getRate());
        restaurantService.create(restaurant);
    }

}
