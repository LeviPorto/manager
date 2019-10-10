package com.levi.manager.listener;

import com.levi.manager.dto.EvaluatedRestaurantDTO;
import com.levi.manager.domain.Restaurant;

public interface UpdateRatingListener {

    void ratingWasUpdated(EvaluatedRestaurantDTO evaluatedRestaurantDTO, Restaurant restaurant);

}
