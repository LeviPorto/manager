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
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

//TODO Extrair o loop tendo o distanceCalculator dentro

//TODO Ver a ideia de tornar mais funcional, utilizando construtores (ideia eh tirar os setters e refatorar todo código baseado nisso, criando construtores para cada caso)

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

        //TODO Fazer Fill do payment acceptance (usando feign para pegar esses dados em batch)

        for (RestaurantFilter restaurantFilter : restaurantFilters) {
            userCityRestaurants = restaurantFilter.filterRestaurant(restaurantSearchDTO, userCityRestaurants);
        }

        return sortFilteredRestaurants(userCityRestaurants, restaurantSearchDTO.getSortSearch());
    }

    public Restaurant retrieveByDeliveryMan(Integer deliveryManId) {
        return repository.findByDeliveryManId(deliveryManId);
    }

    private List<FilteredRestaurantDTO> sortFilteredRestaurants(List<FilteredRestaurantDTO> userCityRestaurants, SortSearch sortSearch) {
        switch (sortSearch) {
            case HIGHEST_RATED:
                //TODO por em ordem decrescente
                return userCityRestaurants.stream().sorted(Comparator.comparingDouble(restaurant -> repository.findById(restaurant.getRestaurantId()).get().getRating())).collect(Collectors.toList());
            case SHORTEST_DELIVERY_FEE:
                return userCityRestaurants.stream().sorted(Comparator.comparingDouble(FilteredRestaurantDTO::getDeliveryFee)).collect(Collectors.toList());
            case SHORTEST_DISTANCE:
                return userCityRestaurants.stream().sorted(Comparator.comparingDouble(FilteredRestaurantDTO::getDistanceFromCustomer)).collect(Collectors.toList());
            case SHORTEST_DELIVERY_TIME:
                return userCityRestaurants.stream().sorted(Comparator.comparingDouble(FilteredRestaurantDTO::getDeliveryTime)).collect(Collectors.toList());
            case SHORTEST_DELIVERY_PRICE:
                return userCityRestaurants.stream().sorted(Comparator.comparingDouble(restaurant -> repository.findById(restaurant.getRestaurantId()).get().getCost())).collect(Collectors.toList());
            case DEFAULT:
                return userCityRestaurants;
        }
        return userCityRestaurants;
    }

    private void fillFilteredRestaurantsWithDeliveryFee(User user, List<FilteredRestaurantDTO> userCityRestaurants) {
        userCityRestaurants.forEach(userCityRestaurant -> {
            Double deliveryFee = distanceCalculatorService.calculateRestaurantDeliveryFeeBasedOnDistance(user, userCityRestaurant.getRestaurantId(), userCityRestaurant);
            userCityRestaurant.setDeliveryFee(deliveryFee);
        });
    }

    private void fillFilteredRestaurantsWithDeliveryTime(User user, List<FilteredRestaurantDTO> userCityRestaurants) {
        userCityRestaurants.forEach(userCityRestaurant -> {
            Double deliveryFee = distanceCalculatorService.calculateRestaurantDeliveryTimeBasedOnDistance(user, userCityRestaurant.getRestaurantId(), userCityRestaurant);
            userCityRestaurant.setDeliveryTime(deliveryFee);
        });
    }

    private void fillFilteredRestaurantsWithDeliveryDistance(User user, List<FilteredRestaurantDTO> userCityRestaurants) {
        userCityRestaurants.forEach(userCityRestaurant -> {
            Double deliveryFee = distanceCalculatorService.calculateRestaurantDefaultDeliveryRadius(user, userCityRestaurant.getRestaurantId(), userCityRestaurant);
            userCityRestaurant.setDistanceFromCustomer(deliveryFee);
        });
    }

}
