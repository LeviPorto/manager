package com.levi.manager.controller;

import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import com.levi.manager.entity.Restaurant;
import com.levi.manager.service.RestaurantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/manager/restaurant")
public class RestaurantController {

    private final RestaurantService service;

    public RestaurantController(final RestaurantService service) {
        this.service = service;
    }

    @PostMapping
    public Restaurant create(@RequestBody Restaurant restaurant) {
        return service.create(restaurant);
    }

    @PutMapping("/{id}")
    public Restaurant update(@RequestBody Restaurant restaurant, @PathVariable Integer id) {
        return service.update(restaurant, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.remove(id);
    }

    @PostMapping("/search")
    public List<FilteredRestaurantDTO> search(@RequestBody RestaurantSearchDTO restaurantSearchDTO) {
        return service.retrieveFilteredRestaurants(restaurantSearchDTO);
    }

}
