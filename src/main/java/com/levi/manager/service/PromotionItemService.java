package com.levi.manager.service;

import com.levi.manager.crud.AbstractCrudService;
import com.levi.manager.domain.PromotionItem;
import com.levi.manager.repository.PromotionItemRepository;
import org.springframework.stereotype.Service;

@Service
public class PromotionItemService extends AbstractCrudService<PromotionItem> {

    public PromotionItemService(final PromotionItemRepository repository) {
        super(repository);
    }

}
