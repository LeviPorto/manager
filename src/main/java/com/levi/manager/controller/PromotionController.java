package com.levi.manager.controller;

import com.levi.manager.entity.Promotion;
import com.levi.manager.service.PromotionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager/promotion")
public class PromotionController {

    private final PromotionService service;

    public PromotionController(final PromotionService service) {
        this.service = service;
    }

    @PostMapping
    public Promotion create(@RequestBody Promotion promotion) {
        return service.create(promotion);
    }

    @PutMapping("/{id}")
    public Promotion update(@RequestBody Promotion promotion, @PathVariable Integer id) {
        return service.update(promotion, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.remove(id);
    }

    @GetMapping
    public List<Promotion> getFilteredPromotions(@RequestParam String searchedName, @RequestParam String userCity) {
        return service.retrieveFilteredPromotions(searchedName, userCity);
    }

}
