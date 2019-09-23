package com.levi.manager.service;

import com.levi.manager.entity.User;
import com.levi.manager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(final UserRepository repository) {
        this.repository = repository;
    }

    public User create(User user) {
        return repository.save(user);
    }

    public User update(User user, Integer id) {
        user.setId(id);
        return repository.save(user);
    }

    public void remove(Integer userId) {
        repository.deleteById(userId);
    }

    public Optional<User> retrieveById(Integer id) {
        return repository.findById(id);
    }

    public List<User> retrieveByIds(List<Integer> ids) {
        return repository.findByIdIn(ids);
    }

}
