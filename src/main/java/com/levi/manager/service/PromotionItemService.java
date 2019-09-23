package com.levi.manager.service;

import com.levi.manager.entity.PromotionItem;
import com.levi.manager.repository.PromotionItemRepository;
import org.springframework.stereotype.Service;

@Service
public class PromotionItemService {

    private final PromotionItemRepository repository;

    public PromotionItemService(final PromotionItemRepository repository) {
        this.repository = repository;
    }

    public PromotionItem create(PromotionItem promotionItem) {
        return repository.save(promotionItem);
    }

    public void remove(Integer id) {
        repository.deleteById(id);
    }


}
