package com.levi.manager.filter;

import com.levi.manager.dto.CardDTO;
import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.PaymentAcceptanceDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;

@Component
public class RestaurantFilterByCardPaymentAcceptance implements RestaurantFilter {

    @Override
    public List<FilteredRestaurantDTO> filterRestaurant(RestaurantSearchDTO restaurantSearchDTO, List<FilteredRestaurantDTO> userCityRestaurants) {
        PaymentAcceptanceDTO paymentAcceptanceDTO = restaurantSearchDTO.getPaymentAcceptanceDTO();
        if(paymentAcceptanceDTO != null) {
            List<CardDTO> cards = paymentAcceptanceDTO.getCardsDTOs();
            if (isNotEmpty(cards)) {
                return userCityRestaurants.stream().filter(userCityRestaurant -> cards
                        .stream().anyMatch(cardDTO -> userCityRestaurant.getPaymentAcceptanceDTO().getCardsDTOs()
                                .stream().anyMatch(otherCardDTO -> otherCardDTO.equals(cardDTO)))).collect(Collectors.toList());
            }
        }
        return userCityRestaurants;

    }
}
