package com.levi.manager.services;

import com.levi.manager.daos.RestaurantDao;
import com.levi.manager.dtos.RestaurantFilteredDTO;
import com.levi.manager.dtos.RestaurantSearchDTO;
import com.levi.manager.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantDao dao;
    private final UserService userService;

    public RestaurantService(final RestaurantDao dao, final UserService userService) {
        this.dao = dao;
        this.userService = userService;
    }

    public List<RestaurantFilteredDTO> retrieveFilteredRestaurants(RestaurantSearchDTO restaurantSearchDTO) {
        Optional<User> loggedUser = userService.retrieveById(restaurantSearchDTO.getUserId());
        return dao.findFilteredRestaurants(restaurantSearchDTO);
    }

}
