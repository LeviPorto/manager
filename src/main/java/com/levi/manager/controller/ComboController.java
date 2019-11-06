package com.levi.manager.controller;

import com.levi.manager.crud.AbstractCrudController;
import com.levi.manager.domain.Combo;
import com.levi.manager.service.ComboService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager/combo")
public class ComboController extends AbstractCrudController<Combo> {

    private final ComboService service;

    public ComboController(final ComboService service) {
        super(service);
        this.service = service;
    }

    @GetMapping("/search")
    public List<Combo> search(@RequestParam String searchedName, @RequestParam String userCity, @RequestParam Integer userId) {
        return service.retrieveFilteredCombos(searchedName, userCity, userId);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<Combo> findByRestaurant(@PathVariable Integer restaurantId) {
        return service.retrieveByRestaurant(restaurantId);
    }

}
