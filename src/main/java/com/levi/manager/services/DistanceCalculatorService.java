package com.levi.manager.services;

import com.levi.manager.dtos.RestaurantFilteredDTO;
import com.levi.manager.entities.Restaurant;
import com.levi.manager.entities.User;
import org.springframework.stereotype.Service;

import static com.levi.manager.util.DistanceCalculatorUtil.calculateDistanceBetweenPoints;

@Service
public class DistanceCalculatorService {

    private final UserService userService;
    private final RestaurantService restaurantService;

    private final static Double NORMAL_SPEED_M_PER_SECONDS = 8.33;
    public final static Double DEFAULT_DELIVERY_RADIUS_IN_METERS = 2000.0;

    public DistanceCalculatorService(final UserService userService, final RestaurantService restaurantService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    public Double calculateRestaurantDefaultDeliveryRadius(Integer userId, Integer restaurantId, RestaurantFilteredDTO restaurantFilteredDTO) {
        User user = userService.retrieveById(userId).get();
        Restaurant restaurant = restaurantService.retrieveById(restaurantId);

        Double calculatedDistanceFromRestaurantToCustomer = calculateDistanceBetweenPoints(user.getLatitude(), user.getLongitude(), restaurant.getLatitude(), restaurant.getLongitude());
        restaurantFilteredDTO.setDistanceFromCustomer(calculatedDistanceFromRestaurantToCustomer);

        return calculatedDistanceFromRestaurantToCustomer;
    }

    public Double calculateRestaurantDeliveryFeeBasedOnDistance(Integer userId, Integer restaurantId, RestaurantFilteredDTO restaurantFilteredDTO) {
        User user = userService.retrieveById(userId).get();
        Restaurant restaurant = restaurantService.retrieveById(restaurantId);

        Double distanceFromUserToRestaurant = calculateDistanceBetweenPoints(user.getLatitude(), user.getLongitude(), restaurant.getLatitude(), restaurant.getLongitude());
        Double restaurantDeliveryFee = distanceFromUserToRestaurant * restaurant.getDeliveryFee();

        restaurantFilteredDTO.setDeliveryFee(restaurantDeliveryFee);

        return restaurantDeliveryFee;
    }

    public Double calculateRestaurantDeliveryTimeBasedOnDistance(Integer userId, Integer restaurantId, RestaurantFilteredDTO restaurantFilteredDTO) {
        User user = userService.retrieveById(userId).get();
        Restaurant restaurant = restaurantService.retrieveById(restaurantId);

        Double distanceFromUserToRestaurant = calculateDistanceBetweenPoints(user.getLatitude(), user.getLongitude(), restaurant.getLatitude(), restaurant.getLongitude());
        Double restaurantDeliveryTime = distanceFromUserToRestaurant / NORMAL_SPEED_M_PER_SECONDS;

        restaurantFilteredDTO.setDeliveryTime(restaurantDeliveryTime);

        return restaurantDeliveryTime;
    }

}
