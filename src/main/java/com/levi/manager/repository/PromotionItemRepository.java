package com.levi.manager.repository;

import com.levi.manager.entity.PromotionItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionItemRepository extends CrudRepository<PromotionItem, Integer> {
}
