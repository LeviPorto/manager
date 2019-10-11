package com.levi.manager.service;

import com.levi.manager.crud.AbstractCrudService;
import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import com.levi.manager.domain.Promotion;
import com.levi.manager.repository.PromotionRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

    @Cacheable(value = "PROMOTIONS_BY_RESTAURANT_ID_", key = "{#restaurantId}", unless = "#result == null || #result.isEmpty()")
    public List<Promotion> retrieveByRestaurant(Integer restaurantId) {
        return repository.findByRestaurantId(restaurantId);
    }

    @Caching(evict = {@CacheEvict(value = "PROMOTIONS_BY_RESTAURANT_ID_", key = "{#promotion.restaurant.id}")})
    public Promotion create(Promotion promotion) {
        return repository.save(promotion);
    }

}
