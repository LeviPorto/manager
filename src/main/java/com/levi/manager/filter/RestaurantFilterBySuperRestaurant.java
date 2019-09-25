package com.levi.manager.filter;

import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantFilterBySuperRestaurant implements RestaurantFilter {

    @Override
    public List<FilteredRestaurantDTO> filterRestaurant(RestaurantSearchDTO restaurantSearchDTO, List<FilteredRestaurantDTO> userCityRestaurants) {
        boolean isSuperRestaurant = restaurantSearchDTO.isSuperRestaurant();
        if (isSuperRestaurant) {
            return userCityRestaurants.stream().filter(FilteredRestaurantDTO::isSuperRestaurant).collect(Collectors.toList());
        } else {
            return userCityRestaurants;
        }
    }

}
