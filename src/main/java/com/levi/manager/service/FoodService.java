package com.levi.manager.service;

import com.levi.manager.crud.AbstractCrudService;
import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import com.levi.manager.domain.Food;
import com.levi.manager.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodService extends AbstractCrudService<Food> {

    private final RestaurantService restaurantService;
    private final FoodRepository repository;

    public FoodService(final FoodRepository repository, final RestaurantService restaurantService, FoodRepository foodRepository, FoodRepository repository1) {
        super(repository);
        this.restaurantService = restaurantService;
        this.repository = repository;
    }

    public List<Food> retrieveFilteredFoods(String searchedName, String userCity) {
        List<FilteredRestaurantDTO> filteredRestaurants = restaurantService.retrieveFilteredRestaurants(RestaurantSearchDTO.builder().userCity(userCity).build());
        List<Food> filteredFoods = new ArrayList<>();

        if (searchedName != null) {
            filteredRestaurants.forEach(restaurant -> restaurant.getFoods().stream()
                    .filter(food -> food.getName().equals(searchedName)).forEach(filteredFoods::add));
        } else {
            filteredRestaurants.forEach(restaurant -> filteredFoods.addAll(restaurant.getFoods()));
        }

        return filteredFoods;
    }

    public List<Food> retrieveByRestaurant(Integer restaurantId) {
        return repository.findByRestaurantId(restaurantId);
    }


}
