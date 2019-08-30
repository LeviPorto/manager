package com.levi.manager.services;

import com.levi.manager.dtos.RestaurantFilteredDTO;
import com.levi.manager.dtos.RestaurantSearchDTO;
import com.levi.manager.entities.Food;
import com.levi.manager.repositories.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodService {

    private final FoodRepository repository;
    private final RestaurantService restaurantService;

    public FoodService(final FoodRepository repository, final RestaurantService restaurantService) {
        this.repository = repository;
        this.restaurantService = restaurantService;
    }

    public List<Food> retrieveFilteredFoods(String searchedName, String userCity) {
        List<RestaurantFilteredDTO> filteredRestaurants = restaurantService.retrieveFilteredRestaurants(RestaurantSearchDTO.builder().userCity(userCity).build());
        List<Food> filteredFoods = new ArrayList<>();
        filteredRestaurants.forEach(restaurant -> {
            restaurant.getFoods().stream().filter(food -> food.equals(searchedName)).forEach(filteredFoods::add);
        });
        return filteredFoods;
    }

}
