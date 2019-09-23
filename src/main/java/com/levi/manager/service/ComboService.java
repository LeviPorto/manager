package com.levi.manager.service;

import com.levi.manager.dto.RestaurantFilteredDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import com.levi.manager.entity.Combo;
import com.levi.manager.repository.ComboRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComboService {

    private final ComboRepository repository;
    private final RestaurantService restaurantService;

    public ComboService(final ComboRepository repository, final RestaurantService restaurantService) {
        this.repository = repository;
        this.restaurantService = restaurantService;
    }

    public Combo create(Combo combo) {
        return repository.save(combo);
    }

    public Combo update(Combo combo, Integer id) {
        combo.setId(id);
        return repository.save(combo);
    }

    public void remove(Integer id) {
        repository.deleteById(id);
    }

    public List<Combo> retrieveFilteredCombos(String searchedName, String userCity) {
        List<RestaurantFilteredDTO> filteredRestaurants = restaurantService.retrieveFilteredRestaurants(RestaurantSearchDTO.builder().userCity(userCity).build());
        List<Combo> filteredCombos = new ArrayList<>();

        if(searchedName != null) {
            filteredRestaurants.forEach(restaurant -> restaurant.getCombos().stream()
                    .filter(combo -> combo.getName().equals(searchedName)).forEach(filteredCombos::add));
        } else {
            filteredRestaurants.forEach(restaurant -> filteredCombos.addAll(restaurant.getCombos()));
        }

        return filteredCombos;
    }

}
