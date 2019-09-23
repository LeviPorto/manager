package com.levi.manager.controller;

import com.levi.manager.entity.Food;
import com.levi.manager.service.FoodService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager/food")
public class FoodController {

    private final FoodService service;

    public FoodController(final FoodService service) {
        this.service = service;
    }

    @PostMapping
    public Food create(@RequestBody Food food) {
        return service.create(food);
    }

    @PutMapping("/{id}")
    public Food update(@RequestBody Food food, @PathVariable Integer id) {
        return service.update(food, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.remove(id);
    }

    @GetMapping
    public List<Food> getFilteredFoods(@RequestParam String searchedName, @RequestParam String userCity) {
        return service.retrieveFilteredFoods(searchedName, userCity);
    }

}
