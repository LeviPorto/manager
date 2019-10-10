package com.levi.manager.crud;

import java.util.List;

public abstract class AbstractCrudService<T extends IdentifiedEntity> {

    private final AbstractCrudRepository<T> repository;

    public AbstractCrudService(final AbstractCrudRepository<T> repository) {
        this.repository = repository;
    }

    public T retrieveById(Integer id) {
        return repository.findById(id).get();
    }

    public List<T> retrieveAll() {
        return repository.findAll();
    }

    public T create(T entity) {
        return repository.save(entity);
    }

    public T update(T combo, Integer id) {
        combo.setId(id);
        return repository.save(combo);
    }

    public void remove(Integer id) {
        repository.deleteById(id);
    }

}
