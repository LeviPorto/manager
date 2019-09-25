package com.levi.manager.filter;

import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantFilterByDeliveryFee implements RestaurantFilter {

    @Override
    public List<FilteredRestaurantDTO> filterRestaurant(RestaurantSearchDTO restaurantSearchDTO, List<FilteredRestaurantDTO> userCityRestaurants) {
        Integer deliveryFeeFilter = restaurantSearchDTO.getDeliveryFee();
        if (deliveryFeeFilter != null) {
            return userCityRestaurants.stream().filter(userCityRestaurant -> userCityRestaurant.getDeliveryFee() < deliveryFeeFilter).collect(Collectors.toList());
        } else {
            return userCityRestaurants;
        }
    }

}
