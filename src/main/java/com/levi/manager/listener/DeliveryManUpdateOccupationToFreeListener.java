package com.levi.manager.listener;

import com.levi.manager.domain.DeliveryMan;
import com.levi.manager.domain.Restaurant;
import com.levi.manager.domain.enumeration.Occupation;
import com.levi.manager.dto.CoordinateDTO;
import com.levi.manager.service.DeliveryManService;
import org.springframework.stereotype.Component;

import static com.levi.manager.util.DistanceCalculatorUtil.calculateDistanceBetweenPoints;

@Component
public class DeliveryManUpdateOccupationToFreeListener implements CoordinateCreatedListener {

    private final DeliveryManService deliveryManService;

    private static final int DISTANCE_CONSIDERED_DELIVERED = 200;

    public DeliveryManUpdateOccupationToFreeListener(DeliveryManService deliveryManService) {
        this.deliveryManService = deliveryManService;
    }

    @Override
    public void coordinateWasCreated(CoordinateDTO coordinateDTO) {
        DeliveryMan deliveryMan = deliveryManService.retrieveById(coordinateDTO.getDeliveryManId());
        Restaurant restaurant = deliveryMan.getRestaurant();
        if(arrivedAtDestination(coordinateDTO, restaurant)) {
            deliveryMan.setOccupation(Occupation.FREE);
            deliveryManService.update(deliveryMan, deliveryMan.getId());
        }
    }

    private boolean arrivedAtDestination(CoordinateDTO coordinateDTO, Restaurant restaurant) {
        return calculateDistanceBetweenPoints(coordinateDTO.getLatitude(), coordinateDTO.getLongitude(),
                restaurant.getLatitude(), restaurant.getLongitude()) < DISTANCE_CONSIDERED_DELIVERED;
    }
}

