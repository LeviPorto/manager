package com.levi.manager.crud;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface AbstractCrudRepository<T extends IdentifiedEntity> extends CrudRepository<T, Integer> {

    <S extends T> S save(S entity);

    void deleteById(Integer id);

    Optional<T> findById(Integer id);

    List<T> findAll();

}
