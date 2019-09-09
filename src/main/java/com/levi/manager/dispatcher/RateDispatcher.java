package com.levi.manager.dispatcher;

import com.levi.manager.dtos.AvaliatedRestaurantDTO;
import com.levi.manager.entities.Restaurant;
import com.levi.manager.listener.UpdateRateListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RateDispatcher {

    private final List<UpdateRateListener> updateRateListeners;

    @Autowired
    public RateDispatcher(List<UpdateRateListener> updateRateListeners) {
        this.updateRateListeners = updateRateListeners;
    }

    public void notifyUpdateRateListeners(final AvaliatedRestaurantDTO avaliatedRestaurantDTO, final Restaurant restaurant) {
        if (updateRateListeners != null) {
            for (UpdateRateListener listener : updateRateListeners) {
                listener.rateWasUpdated(avaliatedRestaurantDTO, restaurant);
            }
        }
    }

}
