package com.levi.manager.service;

import com.levi.manager.crud.AbstractCrudService;
import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import com.levi.manager.domain.Promotion;
import com.levi.manager.repository.PromotionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PromotionService extends AbstractCrudService<Promotion> {

    private final RestaurantService restaurantService;
    private final PromotionRepository repository;

    public PromotionService(final PromotionRepository repository, final RestaurantService restaurantService, PromotionRepository repository1) {
        super(repository);
        this.restaurantService = restaurantService;
        this.repository = repository1;
    }

    public List<Promotion> retrieveFilteredPromotions(String searchedName, String userCity) {
        List<FilteredRestaurantDTO> filteredRestaurants = restaurantService.retrieveFilteredRestaurants(RestaurantSearchDTO.builder().userCity(userCity).build());
        List<Promotion> filteredPromotions = new ArrayList<>();

        if (searchedName != null) {
            filteredRestaurants.forEach(restaurant -> restaurant.getPromotions().stream()
                    .filter(promotion -> promotion.getName().equals(searchedName)).forEach(filteredPromotions::add));
        } else {
            filteredRestaurants.forEach(restaurant -> filteredPromotions.addAll(restaurant.getPromotions()));
        }

        return filteredPromotions;
    }

    public List<Promotion> retrieveByRestaurant(Integer restaurantId) {
        return repository.findByRestaurantId(restaurantId);
    }

}
