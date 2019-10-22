package com.levi.manager.service;

import com.levi.manager.crud.AbstractCrudService;
import com.levi.manager.dao.RestaurantDao;
import com.levi.manager.domain.User;
import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import com.levi.manager.dto.enumeration.SortSearch;
import com.levi.manager.domain.Restaurant;
import com.levi.manager.filter.RestaurantFilter;
import com.levi.manager.repository.RestaurantRepository;
import com.levi.manager.service.nontransactional.DistanceCalculatorService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RestaurantService extends AbstractCrudService<Restaurant> {

    private final RestaurantDao dao;
    private final RestaurantRepository repository;
    private final DistanceCalculatorService distanceCalculatorService;
    private final List<RestaurantFilter> restaurantFilters;
    private final UserService userService;

    public RestaurantService(final RestaurantDao dao, final RestaurantRepository repository,
                             final DistanceCalculatorService distanceCalculatorService, List<RestaurantFilter> restaurantFilters, UserService userService) {
        super(repository);
        this.dao = dao;
        this.repository = repository;
        this.distanceCalculatorService = distanceCalculatorService;
        this.restaurantFilters = restaurantFilters;
        this.userService = userService;
    }

    public List<FilteredRestaurantDTO> retrieveFilteredRestaurants(RestaurantSearchDTO restaurantSearchDTO) {
        List<FilteredRestaurantDTO> userCityRestaurants = dao.findUserCityRestaurants(restaurantSearchDTO);
        User user = userService.retrieveById(restaurantSearchDTO.getUserId());

        fillFilteredRestaurantsWithDeliveryFee(user, userCityRestaurants);
        fillFilteredRestaurantsWithDeliveryTime(user, userCityRestaurants);
        fillFilteredRestaurantsWithDeliveryDistance(user, userCityRestaurants);

        if(!restaurantFilters.isEmpty()) {
            for (RestaurantFilter restaurantFilter : restaurantFilters) {
                userCityRestaurants = restaurantFilter.filterRestaurant(restaurantSearchDTO, userCityRestaurants);
            }
        }

        return sortFilteredRestaurants(userCityRestaurants, restaurantSearchDTO.getSortSearch());
    }

    @Cacheable(value = "RESTAURANTS_BY_DELIVERY_MAN_ID_", key = "{#deliveryManId}", unless = "#result == null")
    public Restaurant retrieveByDeliveryMan(Integer deliveryManId) {
        return repository.findByDeliveryManId(deliveryManId);
    }

    @Caching(evict = {@CacheEvict(value = "RESTAURANTS_BY_DELIVERY_MAN_ID_", key = "{#restaurant.deliveryMan.id}")})
    public Restaurant create(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    private List<FilteredRestaurantDTO> sortFilteredRestaurants(List<FilteredRestaurantDTO> userCityRestaurants, SortSearch sortSearch) {
        switch (sortSearch) {
            case HIGHEST_RATED:
                return userCityRestaurants.stream().sorted(Comparator.comparingDouble(restaurant -> ((FilteredRestaurantDTO) restaurant).getRating()).reversed()).collect(Collectors.toList());
            case SHORTEST_DELIVERY_FEE:
                return userCityRestaurants.stream().sorted(Comparator.comparingDouble(FilteredRestaurantDTO::getDeliveryFee)).collect(Collectors.toList());
            case SHORTEST_DISTANCE:
                return userCityRestaurants.stream().sorted(Comparator.comparingDouble(FilteredRestaurantDTO::getDistanceFromCustomer)).collect(Collectors.toList());
            case SHORTEST_DELIVERY_TIME:
                return userCityRestaurants.stream().sorted(Comparator.comparingDouble(FilteredRestaurantDTO::getDeliveryTime)).collect(Collectors.toList());
            case DEFAULT:
                return userCityRestaurants;
        }
        return userCityRestaurants;
    }

    private void fillFilteredRestaurantsWithDeliveryFee(User user, List<FilteredRestaurantDTO> userCityRestaurants) {
        userCityRestaurants.forEach(userCityRestaurant -> {
            Double deliveryFee = distanceCalculatorService.calculateRestaurantDeliveryFeeBasedOnDistance(user, userCityRestaurant);
            userCityRestaurant.setDeliveryFee(deliveryFee);
        });
    }

    private void fillFilteredRestaurantsWithDeliveryTime(User user, List<FilteredRestaurantDTO> userCityRestaurants) {
        userCityRestaurants.forEach(userCityRestaurant -> {
            Double deliveryFee = distanceCalculatorService.calculateRestaurantDeliveryTimeBasedOnDistance(user, userCityRestaurant);
            userCityRestaurant.setDeliveryTime(deliveryFee);
        });
    }

    private void fillFilteredRestaurantsWithDeliveryDistance(User user, List<FilteredRestaurantDTO> userCityRestaurants) {
        userCityRestaurants.forEach(userCityRestaurant -> {
            Double deliveryFee = distanceCalculatorService.calculateRestaurantDefaultDeliveryRadius(user, userCityRestaurant);
            userCityRestaurant.setDistanceFromCustomer(deliveryFee);
        });
    }

}
