package com.levi.manager.repository;

import com.levi.manager.crud.AbstractCrudRepository;
import com.levi.manager.domain.ComboItem;
import org.springframework.stereotype.Repository;

@Repository
public interface ComboItemRepository extends AbstractCrudRepository<ComboItem> {
}
