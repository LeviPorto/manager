package com.levi.manager.service;

import com.levi.manager.entity.ComboItem;
import com.levi.manager.repository.ComboItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ComboItemService {

    private final ComboItemRepository repository;

    public ComboItemService(final ComboItemRepository repository) {
        this.repository = repository;
    }

    public ComboItem create(ComboItem comboItem) {
        return repository.save(comboItem);
    }

    public void remove(Integer id) {
        repository.deleteById(id);
    }


}
