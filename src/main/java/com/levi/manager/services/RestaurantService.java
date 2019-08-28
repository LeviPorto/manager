package com.levi.manager.services;

import com.levi.manager.daos.RestaurantDao;
import com.levi.manager.dtos.RestaurantFilteredDTO;
import com.levi.manager.dtos.RestaurantSearchDTO;
import com.levi.manager.entities.Restaurant;
import com.levi.manager.entities.User;
import com.levi.manager.entities.enuns.RestaurantCategory;
import com.levi.manager.repositories.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.levi.manager.services.DistanceCalculatorService.DEFAULT_DELIVERY_RADIUS_IN_METERS;
import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;

//TODO Melhorar o retrieve de restaurantes
//TODO Verificar se cabe por uma interface de filtros
//TODO Extrair o loop tendo o distanceCalculator dentro

//TODO Dao só vai servir para trazer os restaurantes da cidade, com a projeção necessária para exibição e calculo. Service vai fazer os filtors customizados

@Service
public class RestaurantService {

    private final RestaurantDao dao;
    private final RestaurantRepository repository;
    private final DistanceCalculatorService distanceCalculatorService;


    public RestaurantService(final RestaurantDao dao, final RestaurantRepository repository, final UserService userService,
                             final DistanceCalculatorService distanceCalculatorService) {
        this.dao = dao;
        this.repository = repository;
        this.distanceCalculatorService = distanceCalculatorService;
    }

    public Restaurant create(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    public Restaurant retrieveById(Integer id) {
        return repository.findById(id).get();
    }

    public List<RestaurantFilteredDTO> retrieveFilteredRestaurants(RestaurantSearchDTO restaurantSearchDTO) {

        List<RestaurantFilteredDTO> orderedUserCityRestaurants = dao.findUserCityRestaurants(restaurantSearchDTO);

        orderedUserCityRestaurants = filterByDefaultRadiusDistance(restaurantSearchDTO.getUserId(), orderedUserCityRestaurants);

        orderedUserCityRestaurants = filterByCategory(restaurantSearchDTO.getCategories(), orderedUserCityRestaurants);
        orderedUserCityRestaurants = filterByDeliveryFee(restaurantSearchDTO.getDeliveryFee(), restaurantSearchDTO.getUserId(), orderedUserCityRestaurants);
        orderedUserCityRestaurants = filterByDeliveryTime(restaurantSearchDTO.getDeliveryTime(), restaurantSearchDTO.getUserId(), orderedUserCityRestaurants);
        orderedUserCityRestaurants = filterBySuperRestaurant(restaurantSearchDTO.getIsSuperRestaurant(), orderedUserCityRestaurants);
        orderedUserCityRestaurants = filterByTrackedDelivery(restaurantSearchDTO.getHasTrackedDelivery(), orderedUserCityRestaurants);
        orderedUserCityRestaurants = filterByIFoodDelivery(restaurantSearchDTO.getIsIFoodDelivery(), orderedUserCityRestaurants);

        //TODO Filtros por Payment Method

    }

    private List<RestaurantFilteredDTO> filterByDefaultRadiusDistance(Integer userId, List<RestaurantFilteredDTO> orderedUserCityRestaurants) {
        return orderedUserCityRestaurants.stream().filter(userCityRestaurant -> distanceCalculatorService
                    .calculateRestaurantDefaultDeliveryRadius(userId, userCityRestaurant.getRestaurantId()) < DEFAULT_DELIVERY_RADIUS_IN_METERS).collect(Collectors.toList());
    }

    private List<RestaurantFilteredDTO> filterByCategory(List<RestaurantCategory> categoriesFilter, List<RestaurantFilteredDTO> orderedUserCityRestaurants) {
        if (isNotEmpty(categoriesFilter)) {
            return orderedUserCityRestaurants.stream().filter(userCityRestaurant -> categoriesFilter.stream()
                    .anyMatch(category -> userCityRestaurant.restaurantCategory.equals(category))).collect(Collectors.toList());
        } else {
            return orderedUserCityRestaurants;
        }
    }


    private List<RestaurantFilteredDTO> filterByDeliveryFee(Integer deliveryFeeFilter, Integer userId, List<RestaurantFilteredDTO> orderedUserCityRestaurants) {
        if (deliveryFeeFilter != null) {
            return orderedUserCityRestaurants.stream().filter(userCityRestaurant -> distanceCalculatorService
                    .calculateRestaurantDeliveryFeeBasedOnDistance(userId, userCityRestaurant.getRestaurantId()) < deliveryFeeFilter).collect(Collectors.toList());
        } else {
            return orderedUserCityRestaurants;
        }
    }


    private List<RestaurantFilteredDTO> filterByDeliveryTime(Integer deliveryTimeFilter, Integer userId, List<RestaurantFilteredDTO> orderedUserCityRestaurants) {
        if (deliveryTimeFilter != null) {
            return orderedUserCityRestaurants.stream().filter(userCityRestaurant -> distanceCalculatorService
                    .calculateRestaurantDeliveryTimeBasedOnDistance(userId, userCityRestaurant.getRestaurantId()) < deliveryTimeFilter).collect(Collectors.toList());
        } else {
            return orderedUserCityRestaurants;
        }
    }

    private List<RestaurantFilteredDTO> filterBySuperRestaurant(Boolean isSuperRestaurant, List<RestaurantFilteredDTO> orderedUserCityRestaurants) {
        if (isSuperRestaurant != null) {
            return orderedUserCityRestaurants.stream().filter(userCityRestaurant -> isSuperRestaurant == userCityRestaurant.getIsSuperRestaurant()).collect(Collectors.toList());
        } else {
            return orderedUserCityRestaurants;
        }
    }

    private List<RestaurantFilteredDTO> filterByTrackedDelivery(Boolean hasTrackedDelivery, List<RestaurantFilteredDTO> orderedUserCityRestaurants) {
        if (hasTrackedDelivery != null) {
            return orderedUserCityRestaurants.stream().filter(userCityRestaurant -> hasTrackedDelivery == userCityRestaurant.getIsSuperRestaurant()).collect(Collectors.toList());
        } else {
            return orderedUserCityRestaurants;
        }
    }

    private List<RestaurantFilteredDTO> filterByIFoodDelivery(Boolean isIFoodDelivery, List<RestaurantFilteredDTO> orderedUserCityRestaurants) {
        if (isIFoodDelivery != null) {
            return orderedUserCityRestaurants.stream().filter(userCityRestaurant -> isIFoodDelivery == userCityRestaurant.getIsSuperRestaurant()).collect(Collectors.toList());
        } else {
            return orderedUserCityRestaurants;
        }
    }

}
