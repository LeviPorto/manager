package com.levi.manager.dispatcher;

import com.levi.manager.dtos.AvaliatedRestaurantDTO;
import com.levi.manager.listener.RateValueListener;
import com.levi.manager.listener.SuperRestaurantListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RateDispatcher {

    @Autowired(required = false)
    private List<RateValueListener> rateValueListeners;

    @Autowired(required = false)
    private List<SuperRestaurantListener> superRestaurantListeners;

    public void notifyRateValueListeners(final AvaliatedRestaurantDTO avaliatedRestaurantDTO) {
        if (rateValueListeners != null) {
            for (RateValueListener listener : rateValueListeners) {
                listener.updateRateValue(avaliatedRestaurantDTO.getRestaurantId(), avaliatedRestaurantDTO.getRate());
            }
        }
    }

    public void notifySuperRestaurantListeners(final AvaliatedRestaurantDTO avaliatedRestaurantDTO) {
        if (superRestaurantListeners != null) {
            for (SuperRestaurantListener listener : superRestaurantListeners) {
                listener.updateIsSuperRestaurant(avaliatedRestaurantDTO.getIsSuperRestaurant());
            }
        }
    }

}
