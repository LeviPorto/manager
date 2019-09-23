package com.levi.manager.controller;

import com.levi.manager.entity.Combo;
import com.levi.manager.service.ComboService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager/combo")
public class ComboController {

    private final ComboService service;

    public ComboController(final ComboService service) {
        this.service = service;
    }

    @PostMapping
    public Combo create(@RequestBody Combo combo) {
        return service.create(combo);
    }

    @PutMapping("/{id}")
    public Combo update(@RequestBody Combo combo, @PathVariable Integer id) {
        return service.update(combo, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.remove(id);
    }

    @GetMapping
    public List<Combo> getFilteredCombos(@RequestParam String searchedName, @RequestParam String userCity) {
        return service.retrieveFilteredCombos(searchedName, userCity);
    }

}
