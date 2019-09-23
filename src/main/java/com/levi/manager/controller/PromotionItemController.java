package com.levi.manager.controller;

import com.levi.manager.entity.PromotionItem;
import com.levi.manager.service.PromotionItemService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager/promotionItem")
public class PromotionItemController {

    private final PromotionItemService service;

    public PromotionItemController(final PromotionItemService service) {
        this.service = service;
    }

    @PostMapping
    public PromotionItem create(@RequestBody PromotionItem promotionItem) {
        return service.create(promotionItem);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.remove(id);
    }

}
