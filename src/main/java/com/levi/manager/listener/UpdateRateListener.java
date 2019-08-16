package com.levi.manager.listener;

import com.levi.manager.dtos.AvaliatedRestaurantDTO;
import com.levi.manager.entities.Restaurant;

public interface UpdateRateListener {

    void rateWasUpdated(AvaliatedRestaurantDTO avaliatedRestaurantDTO, Restaurant restaurant);

}
