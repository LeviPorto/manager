package com.levi.manager.service;

import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import com.levi.manager.entity.Food;
import com.levi.manager.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodService {

    private final FoodRepository repository;
    private final RestaurantService restaurantService;

    public FoodService(final FoodRepository repository, final RestaurantService restaurantService) {
        this.repository = repository;
        this.restaurantService = restaurantService;
    }

    public Food create(Food food) {
        return repository.save(food);
    }

    public Food update(Food food, Integer id) {
        food.setId(id);
        return repository.save(food);
    }

    public void remove(Integer id) {
        repository.deleteById(id);
    }

    public List<Food> retrieveFilteredFoods(String searchedName, String userCity) {
        List<FilteredRestaurantDTO> filteredRestaurants = restaurantService.retrieveFilteredRestaurants(RestaurantSearchDTO.builder().userCity(userCity).build());
        List<Food> filteredFoods = new ArrayList<>();

        if(searchedName != null) {
            filteredRestaurants.forEach(restaurant -> restaurant.getFoods().stream()
                    .filter(food -> food.getName().equals(searchedName)).forEach(filteredFoods::add));
        } else {
            filteredRestaurants.forEach(restaurant -> filteredFoods.addAll(restaurant.getFoods()));
        }

        return filteredFoods;
    }

}
