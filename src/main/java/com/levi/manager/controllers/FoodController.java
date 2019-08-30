package com.levi.manager.controllers;

import com.levi.manager.entities.Food;
import com.levi.manager.services.FoodService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/manager/food")
public class FoodController {

    private final FoodService service;

    public FoodController(final FoodService service) {
        this.service = service;
    }

    @GetMapping
    public List<Food> getFilteredFoods(@RequestParam String searchedName, @RequestParam String userCity) {
        return service.retrieveFilteredFoods(searchedName, userCity);
    }

}
