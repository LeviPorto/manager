package com.levi.manager.listener;

import com.levi.manager.dtos.AvaliatedRestaurantDTO;
import com.levi.manager.entities.Restaurant;

public interface UpdateRatingListener {

    void ratingWasUpdated(AvaliatedRestaurantDTO avaliatedRestaurantDTO, Restaurant restaurant);

}
