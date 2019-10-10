package com.levi.manager.service;

import com.levi.manager.crud.AbstractCrudService;
import com.levi.manager.domain.User;
import com.levi.manager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends AbstractCrudService<User> {

    private final UserRepository repository;

    public UserService(final UserRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<User> retrieveByIds(List<Integer> ids) {
        return repository.findByIdIn(ids);
    }

}
