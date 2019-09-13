package com.levi.manager.listener;

import com.levi.manager.dtos.AvaliatedRestaurantDTO;
import com.levi.manager.entities.Restaurant;
import com.levi.manager.services.RestaurantService;
import org.springframework.stereotype.Component;

//TODO Verificar se uma comida/restaurante pode ter mais de um category

@Component
public class SuperRestaurantListener implements UpdateRatingListener {

    private final RestaurantService restaurantService;

    public SuperRestaurantListener(final RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Override
    public void ratingWasUpdated(AvaliatedRestaurantDTO avaliatedRestaurantDTO, Restaurant restaurant) {
        restaurant.setIsSuperRestaurant(avaliatedRestaurantDTO.isSuperRestaurant());
        restaurantService.create(restaurant);
    }

}
