package com.levi.manager.services;

import com.levi.manager.entities.User;
import com.levi.manager.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(final UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> retrieveById(Integer id) {
        return repository.findById(id);
    }

}
