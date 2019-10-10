package com.levi.manager.service;

import com.levi.manager.crud.AbstractCrudService;
import com.levi.manager.domain.ComboItem;
import com.levi.manager.repository.ComboItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ComboItemService extends AbstractCrudService<ComboItem> {

    public ComboItemService(final ComboItemRepository repository) {
        super(repository);
    }

}
