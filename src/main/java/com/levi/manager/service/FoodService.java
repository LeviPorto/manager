package com.levi.manager.service;

import com.levi.manager.crud.AbstractCrudService;
import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import com.levi.manager.domain.Food;
import com.levi.manager.repository.FoodRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

    @Cacheable(value = "FOODS_BY_RESTAURANT_ID_", key = "{#restaurantId}", unless = "#result == null || #result.isEmpty()")
    public List<Food> retrieveByRestaurant(Integer restaurantId) {
        return repository.findByRestaurantId(restaurantId);
    }

    @Caching(evict = {@CacheEvict(value = "FOODS_BY_RESTAURANT_ID_", key = "{#food.restaurant.id}")})
    public Food create(Food food) {
        return repository.save(food);
    }

}
