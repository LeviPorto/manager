package com.levi.manager.controller;

import com.levi.manager.crud.AbstractCrudController;
import com.levi.manager.domain.Food;
import com.levi.manager.service.FoodService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager/food")
public class FoodController extends AbstractCrudController<Food> {

    private final FoodService service;

    public FoodController(final FoodService service) {
        super(service);
        this.service = service;
    }

    @GetMapping
    public List<Food> getFilteredFoods(@RequestParam String searchedName, @RequestParam String userCity) {
        return service.retrieveFilteredFoods(searchedName, userCity);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<Food> findByRestaurant(@PathVariable Integer restaurantId) {
        return service.retrieveByRestaurant(restaurantId);
    }

}
