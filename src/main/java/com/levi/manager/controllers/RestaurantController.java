package com.levi.manager.controllers;

import com.levi.manager.dtos.RestaurantFilteredDTO;
import com.levi.manager.dtos.RestaurantSearchDTO;
import com.levi.manager.services.RestaurantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/manager/restaurant")
public class RestaurantController {

    private final RestaurantService service;

    public RestaurantController(final RestaurantService service){
        this.service = service;
    }

    @GetMapping
    public List<RestaurantFilteredDTO> getFilteredRestaurants(@RequestParam RestaurantSearchDTO restaurantSearchDTO) {
        return service.retrieveFilteredRestaurants(restaurantSearchDTO);
    }

}
