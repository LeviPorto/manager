package com.levi.manager.controller;

import com.levi.manager.crud.AbstractCrudController;
import com.levi.manager.domain.Promotion;
import com.levi.manager.service.PromotionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager/promotion")
public class PromotionController extends AbstractCrudController<Promotion> {

    private final PromotionService service;

    public PromotionController(final PromotionService service) {
        super(service);
        this.service = service;
    }

    @GetMapping("/search")
    public List<Promotion> getFilteredPromotions(@RequestParam String searchedName, @RequestParam String userCity) {
        return service.retrieveFilteredPromotions(searchedName, userCity);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<Promotion> findByRestaurant(@PathVariable Integer restaurantId) {
        return service.retrieveByRestaurant(restaurantId);
    }
}
