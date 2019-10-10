package com.levi.manager.filter;

import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.levi.manager.service.nontransactional.DistanceCalculatorService.DEFAULT_DELIVERY_RADIUS_IN_METERS;

@Component
public class RestaurantFilterByDefaultRadiusDistance implements RestaurantFilter {

    @Override
    public List<FilteredRestaurantDTO> filterRestaurant(RestaurantSearchDTO restaurantSearchDTO, List<FilteredRestaurantDTO> userCityRestaurants) {
        return userCityRestaurants.stream().filter(userCityRestaurant ->
                userCityRestaurant.getDistanceFromCustomer() < DEFAULT_DELIVERY_RADIUS_IN_METERS).collect(Collectors.toList());
    }

}
