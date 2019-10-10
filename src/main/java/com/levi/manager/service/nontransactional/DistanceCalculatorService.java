package com.levi.manager.service.nontransactional;

import com.levi.manager.domain.User;
import com.levi.manager.dto.FilteredRestaurantDTO;
import org.springframework.stereotype.Service;

import static com.levi.manager.util.DistanceCalculatorUtil.calculateDistanceBetweenPoints;

@Service
public class DistanceCalculatorService {

    private final static Double NORMAL_SPEED_M_PER_SECONDS = 8.33;
    public final static Double DEFAULT_DELIVERY_RADIUS_IN_METERS = 2000.0;

    public Double calculateRestaurantDefaultDeliveryRadius(User user, FilteredRestaurantDTO filteredRestaurantDTO) {
        Double calculatedDistanceFromRestaurantToCustomer = calculateDistanceBetweenPoints(user, filteredRestaurantDTO);
        filteredRestaurantDTO.setDistanceFromCustomer(calculatedDistanceFromRestaurantToCustomer);

        return calculatedDistanceFromRestaurantToCustomer;
    }

    public Double calculateRestaurantDeliveryFeeBasedOnDistance(User user, FilteredRestaurantDTO filteredRestaurantDTO) {
        Double distanceFromUserToRestaurant = calculateDistanceBetweenPoints(user, filteredRestaurantDTO);
        Double restaurantDeliveryFee = distanceFromUserToRestaurant * filteredRestaurantDTO.getDeliveryFee();

        filteredRestaurantDTO.setDeliveryFee(restaurantDeliveryFee);

        return restaurantDeliveryFee;
    }

    public Double calculateRestaurantDeliveryTimeBasedOnDistance(User user, FilteredRestaurantDTO filteredRestaurantDTO) {
        Double distanceFromUserToRestaurant = calculateDistanceBetweenPoints(user, filteredRestaurantDTO);
        Double restaurantDeliveryTime = distanceFromUserToRestaurant / NORMAL_SPEED_M_PER_SECONDS;

        filteredRestaurantDTO.setDeliveryTime(restaurantDeliveryTime);

        return restaurantDeliveryTime;
    }

}
