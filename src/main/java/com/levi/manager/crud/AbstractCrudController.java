package com.levi.manager.crud;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class AbstractCrudController<T extends IdentifiedEntity> {

    private final AbstractCrudService<T> service;

    public AbstractCrudController(final AbstractCrudService<T> service) {
        this.service = service;
    }

    @GetMapping
    public List<T> findAll() {
        return service.retrieveAll();
    }

    @GetMapping("/{id}")
    public T findById(@PathVariable Integer id) {
        return service.retrieveById(id);
    }

    @PostMapping
    public T create(@RequestBody T entity) {
        return service.create(entity);
    }

    @PutMapping("/{id}")
    public T update(@RequestBody T entity, @PathVariable Integer id) {
        return service.update(entity, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.remove(id);
    }

}
