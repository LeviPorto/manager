package com.levi.manager.service;

import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import com.levi.manager.entity.Promotion;
import com.levi.manager.repository.PromotionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PromotionService {

    private final PromotionRepository repository;
    private final RestaurantService restaurantService;

    public PromotionService(final PromotionRepository repository, final RestaurantService restaurantService) {
        this.repository = repository;
        this.restaurantService = restaurantService;
    }

    public Promotion create(Promotion promotion) {
        return repository.save(promotion);
    }

    public Promotion update(Promotion promotion, Integer id) {
        promotion.setId(id);
        return repository.save(promotion);
    }

    public void remove(Integer id) {
        repository.deleteById(id);
    }

    public List<Promotion> retrieveFilteredPromotions(String searchedName, String userCity) {
        List<FilteredRestaurantDTO> filteredRestaurants = restaurantService.retrieveFilteredRestaurants(RestaurantSearchDTO.builder().userCity(userCity).build());
        List<Promotion> filteredPromotions = new ArrayList<>();

        if(searchedName != null) {
            filteredRestaurants.forEach(restaurant -> restaurant.getPromotions().stream()
                    .filter(promotion -> promotion.getName().equals(searchedName)).forEach(filteredPromotions::add));
        } else {
            filteredRestaurants.forEach(restaurant -> filteredPromotions.addAll(restaurant.getPromotions()));
        }

        return filteredPromotions;
    }

}
