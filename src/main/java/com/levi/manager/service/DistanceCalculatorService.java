package com.levi.manager.service;

import com.levi.manager.domain.User;
import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.domain.Restaurant;
import com.levi.manager.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import static com.levi.manager.util.DistanceCalculatorUtil.calculateDistanceBetweenPoints;

@Service
public class DistanceCalculatorService {

    private final RestaurantRepository restaurantRepository;

    private final static Double NORMAL_SPEED_M_PER_SECONDS = 8.33;
    public final static Double DEFAULT_DELIVERY_RADIUS_IN_METERS = 2000.0;

    public DistanceCalculatorService(final RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Double calculateRestaurantDefaultDeliveryRadius(User user, Integer restaurantId, FilteredRestaurantDTO filteredRestaurantDTO) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();

        Double calculatedDistanceFromRestaurantToCustomer = calculateDistanceBetweenPoints(user.getLatitude(), user.getLongitude(), restaurant.getLatitude(), restaurant.getLongitude());
        filteredRestaurantDTO.setDistanceFromCustomer(calculatedDistanceFromRestaurantToCustomer);

        return calculatedDistanceFromRestaurantToCustomer;
    }

    public Double calculateRestaurantDeliveryFeeBasedOnDistance(User user, Integer restaurantId, FilteredRestaurantDTO filteredRestaurantDTO) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();

        Double distanceFromUserToRestaurant = calculateDistanceBetweenPoints(user.getLatitude(), user.getLongitude(), restaurant.getLatitude(), restaurant.getLongitude());
        Double restaurantDeliveryFee = distanceFromUserToRestaurant * restaurant.getDeliveryFee();

        filteredRestaurantDTO.setDeliveryFee(restaurantDeliveryFee);

        return restaurantDeliveryFee;
    }

    public Double calculateRestaurantDeliveryTimeBasedOnDistance(User user, Integer restaurantId, FilteredRestaurantDTO filteredRestaurantDTO) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();

        Double distanceFromUserToRestaurant = calculateDistanceBetweenPoints(user.getLatitude(), user.getLongitude(), restaurant.getLatitude(), restaurant.getLongitude());
        Double restaurantDeliveryTime = distanceFromUserToRestaurant / NORMAL_SPEED_M_PER_SECONDS;

        filteredRestaurantDTO.setDeliveryTime(restaurantDeliveryTime);

        return restaurantDeliveryTime;
    }

}
