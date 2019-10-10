package com.levi.manager.service;

import com.levi.manager.crud.AbstractCrudService;
import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import com.levi.manager.domain.Combo;
import com.levi.manager.repository.ComboRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComboService extends AbstractCrudService<Combo> {

    private final ComboRepository repository;
    private final RestaurantService restaurantService;

    public ComboService(final ComboRepository repository, final RestaurantService restaurantService) {
        super(repository);
        this.repository = repository;
        this.restaurantService = restaurantService;
    }

    public List<Combo> retrieveFilteredCombos(String searchedName, String userCity) {
        List<FilteredRestaurantDTO> filteredRestaurants = restaurantService.retrieveFilteredRestaurants(RestaurantSearchDTO.builder().userCity(userCity).build());
        List<Combo> filteredCombos = new ArrayList<>();

        if (searchedName != null) {
            filteredRestaurants.forEach(restaurant -> restaurant.getCombos().stream()
                    .filter(combo -> combo.getName().equals(searchedName)).forEach(filteredCombos::add));
        } else {
            filteredRestaurants.forEach(restaurant -> filteredCombos.addAll(restaurant.getCombos()));
        }

        return filteredCombos;
    }

    @Cacheable(value = "COMBOS_BY_RESTAURANT_ID_", key = "{#restaurantId}", unless = "#result == null || #result.isEmpty()")
    public List<Combo> retrieveByRestaurant(Integer restaurantId) {
        return repository.findByRestaurantId(restaurantId);
    }

    @Caching(evict = {@CacheEvict(value = "COMBOS_BY_RESTAURANT_ID_", key = "{#combo.restaurant.id}")})
    public Combo create(Combo combo) {
        return repository.save(combo);
    }

}
