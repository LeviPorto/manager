package com.levi.manager.services;

import com.levi.manager.entities.Restaurant;
import com.levi.manager.entities.User;
import org.springframework.stereotype.Service;

import static com.levi.manager.util.DistanceCalculatorUtil.calculateDistanceBetweenPoints;

@Service
public class DistanceCalculatorService {

    private final UserService userService;
    private final RestaurantService restaurantService;

    public DistanceCalculatorService(final UserService userService, final RestaurantService restaurantService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    public Double calculateRestaurantDeliveryFeeBasedOnDistance(Integer userId, Integer restaurantId) {
        User user = userService.retrieveById(userId).get();
        Restaurant restaurant = restaurantService.retrieveById(restaurantId);

        Double distanceFromUserToRestaurant = calculateDistanceBetweenPoints(user.getLatitude(), user.getLongitude(), restaurant.getLatitude(), restaurant.getLongitude());

        return distanceFromUserToRestaurant * restaurant.getCost();
    }

}
