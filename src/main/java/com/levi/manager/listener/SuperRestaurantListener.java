package com.levi.manager.listener;

import com.levi.manager.dtos.AvaliatedRestaurantDTO;
import com.levi.manager.entities.Restaurant;
import com.levi.manager.services.RestaurantService;
import org.springframework.stereotype.Component;

@Component
public class SuperRestaurantListener implements UpdateRateListener {

    private final RestaurantService restaurantService;

    public SuperRestaurantListener(final RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Override
    public void rateWasUpdated(AvaliatedRestaurantDTO avaliatedRestaurantDTO, Restaurant restaurant) {
        restaurant.setIsSuperRestaurant(avaliatedRestaurantDTO.isSuperRestaurant());
        restaurantService.create(restaurant);
    }

}
