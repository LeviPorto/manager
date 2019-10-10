package com.levi.manager.repository;

import com.levi.manager.crud.AbstractCrudRepository;
import com.levi.manager.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends AbstractCrudRepository<User> {

    List<User> findByIdIn(List<Integer> ids);

}
