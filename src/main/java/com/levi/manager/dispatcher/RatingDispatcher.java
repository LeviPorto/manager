package com.levi.manager.dispatcher;

import com.levi.manager.dto.EvaluatedRestaurantDTO;
import com.levi.manager.domain.Restaurant;
import com.levi.manager.listener.UpdateRatingListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RatingDispatcher {

    private final List<UpdateRatingListener> updateRatingListeners;

    @Autowired
    public RatingDispatcher(List<UpdateRatingListener> updateRatingListeners) {
        this.updateRatingListeners = updateRatingListeners;
    }

    public void notifyUpdateRatingListeners(final EvaluatedRestaurantDTO evaluatedRestaurantDTO, final Restaurant restaurant) {
        if (updateRatingListeners != null) {
            for (UpdateRatingListener listener : updateRatingListeners) {
                listener.ratingWasUpdated(evaluatedRestaurantDTO, restaurant);
            }
        }
    }

}
