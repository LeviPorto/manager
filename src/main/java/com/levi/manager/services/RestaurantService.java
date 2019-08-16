package com.levi.manager.services;

import com.levi.manager.daos.RestaurantDao;
import com.levi.manager.dtos.RestaurantFilteredDTO;
import com.levi.manager.dtos.RestaurantSearchDTO;
import com.levi.manager.entities.Restaurant;
import com.levi.manager.entities.User;
import com.levi.manager.repositories.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantDao dao;
    private final RestaurantRepository repository;
    private final UserService userService;


    public RestaurantService(final RestaurantDao dao, final RestaurantRepository repository, final UserService userService) {
        this.dao = dao;
        this.repository = repository;
        this.userService = userService;
    }

    public Restaurant create(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    public Restaurant retrieveById(Integer id) {
        return repository.findById(id).get();
    }

    public List<RestaurantFilteredDTO> retrieveFilteredRestaurants(RestaurantSearchDTO restaurantSearchDTO) {
        Optional<User> loggedUser = userService.retrieveById(restaurantSearchDTO.getUserId());
        return dao.findFilteredRestaurants(restaurantSearchDTO);
    }

}
