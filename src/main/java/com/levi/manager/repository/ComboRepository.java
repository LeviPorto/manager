package com.levi.manager.repository;

import com.levi.manager.entity.Combo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComboRepository extends CrudRepository<Combo, Integer> {
}
