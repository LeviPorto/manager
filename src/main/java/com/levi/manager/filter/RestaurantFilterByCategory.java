package com.levi.manager.filter;

import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import com.levi.manager.domain.enumeration.RestaurantCategory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;

@Component
public class RestaurantFilterByCategory implements RestaurantFilter {

    @Override
    public List<FilteredRestaurantDTO> filterRestaurant(RestaurantSearchDTO restaurantSearchDTO, List<FilteredRestaurantDTO> userCityRestaurants) {
        List<RestaurantCategory> categoriesFilter = restaurantSearchDTO.getCategories();
        if (isNotEmpty(categoriesFilter)) {
            return userCityRestaurants.stream().filter(userCityRestaurant -> categoriesFilter.stream()
                    .anyMatch(category -> userCityRestaurant.restaurantCategory.equals(category))).collect(Collectors.toList());
        } else {
            return userCityRestaurants;
        }
    }

}
