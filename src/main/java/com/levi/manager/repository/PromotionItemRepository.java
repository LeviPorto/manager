package com.levi.manager.repository;

import com.levi.manager.crud.AbstractCrudRepository;
import com.levi.manager.domain.PromotionItem;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionItemRepository extends AbstractCrudRepository<PromotionItem> {
}
