package com.levi.manager.filter;

import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.PaymentAcceptanceDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantFilterPaycheckPaymentAcceptance implements RestaurantFilter {

    @Override
    public List<FilteredRestaurantDTO> filterRestaurant(RestaurantSearchDTO restaurantSearchDTO, List<FilteredRestaurantDTO> userCityRestaurants) {
        PaymentAcceptanceDTO paymentAcceptanceDTO = restaurantSearchDTO.getPaymentAcceptanceDTO();
        if(paymentAcceptanceDTO != null) {
            boolean isPaycheckPaymentAcceptance = paymentAcceptanceDTO.isPaycheck();
            if (isPaycheckPaymentAcceptance) {
                return userCityRestaurants.stream().filter(userCityRestaurant -> userCityRestaurant.getPaymentAcceptanceDTO().isPaycheck()).collect(Collectors.toList());
            }
        }
        return userCityRestaurants;
    }

}
