package com.levi.manager.listener;

import com.levi.manager.dto.AvaliatedRestaurantDTO;
import com.levi.manager.entity.Restaurant;

public interface UpdateRatingListener {

    void ratingWasUpdated(AvaliatedRestaurantDTO avaliatedRestaurantDTO, Restaurant restaurant);

}
