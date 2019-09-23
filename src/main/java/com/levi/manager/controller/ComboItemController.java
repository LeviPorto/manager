package com.levi.manager.controller;

import com.levi.manager.entity.ComboItem;
import com.levi.manager.service.ComboItemService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager/comboItem")
public class ComboItemController {

    private final ComboItemService service;

    public ComboItemController(final ComboItemService service) {
        this.service = service;
    }

    @PostMapping
    public ComboItem create(@RequestBody ComboItem comboItem) {
        return service.create(comboItem);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.remove(id);
    }

}
