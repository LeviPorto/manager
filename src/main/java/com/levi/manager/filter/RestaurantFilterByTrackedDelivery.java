package com.levi.manager.filter;

import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantFilterByTrackedDelivery implements RestaurantFilter {

    @Override
    public List<FilteredRestaurantDTO> filterRestaurant(RestaurantSearchDTO restaurantSearchDTO, List<FilteredRestaurantDTO> userCityRestaurants) {
        boolean hasTrackedDelivery = restaurantSearchDTO.isHasTrackedDelivery();
        if (hasTrackedDelivery) {
            return userCityRestaurants.stream().filter(FilteredRestaurantDTO::isHasTrackedDelivery).collect(Collectors.toList());
        } else {
            return userCityRestaurants;
        }
    }

}
