package com.levi.manager.service;

import com.levi.manager.dao.RestaurantDao;
import com.levi.manager.dto.CardDTO;
import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import com.levi.manager.dto.enumeration.SortSearch;
import com.levi.manager.entity.Restaurant;
import com.levi.manager.entity.enumeration.RestaurantCategory;
import com.levi.manager.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.levi.manager.service.DistanceCalculatorService.DEFAULT_DELIVERY_RADIUS_IN_METERS;
import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;

//TODO Melhorar o retrieve de restaurantes
//TODO Verificar se cabe por uma interface de filtros
//TODO Extrair o loop tendo o distanceCalculator dentro

//TODO Melhorar os Null Pointer Exception nos filtros de Payment Acceptance

//TODO Fazer um filtro só. Cada método retornará um booleano, no qual será aculmulado e passado nesse único filtro

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

    public Restaurant update(Restaurant restaurant, Integer id) {
        restaurant.setId(id);
        return repository.save(restaurant);
    }

    public void remove(Integer id) {
        repository.deleteById(id);
    }

    public Restaurant retrieveById(Integer id) {
        return repository.findById(id).get();
    }

    public List<FilteredRestaurantDTO> retrieveFilteredRestaurants(RestaurantSearchDTO restaurantSearchDTO) {
        List<FilteredRestaurantDTO> userCityRestaurants = dao.findUserCityRestaurants(restaurantSearchDTO);

        fillFilteredRestaurantsWithDeliveryFee(restaurantSearchDTO.getUserId(), userCityRestaurants);
        fillFilteredRestaurantsWithDeliveryTime(restaurantSearchDTO.getUserId(), userCityRestaurants);
        fillFilteredRestaurantsWithDeliveryDistance(restaurantSearchDTO.getUserId(), userCityRestaurants);

        //TODO Fazer Fill do payment acceptance (usando feign para pegar esses dados em batch)

        userCityRestaurants = filterByDefaultRadiusDistance(userCityRestaurants);

        userCityRestaurants = filterByCategory(restaurantSearchDTO.getCategories(), userCityRestaurants);
        userCityRestaurants = filterByDeliveryFee(restaurantSearchDTO.getDeliveryFee(), userCityRestaurants);
        userCityRestaurants = filterByDeliveryTime(restaurantSearchDTO.getDeliveryTime(), userCityRestaurants);
        userCityRestaurants = filterBySuperRestaurant(restaurantSearchDTO.isSuperRestaurant(), userCityRestaurants);
        userCityRestaurants = filterByTrackedDelivery(restaurantSearchDTO.isHasTrackedDelivery(), userCityRestaurants);
        userCityRestaurants = filterByIFoodDelivery(restaurantSearchDTO.isIFoodDelivery(), userCityRestaurants);

        userCityRestaurants = filterByCashPaymentAcceptance(restaurantSearchDTO.getPaymentAcceptanceDTO().isCash(), userCityRestaurants);
        userCityRestaurants = filterByPaycheckPaymentAcceptance(restaurantSearchDTO.getPaymentAcceptanceDTO().isPaycheck(), userCityRestaurants);
        userCityRestaurants = filterByOnlineTicketPaymentAcceptance(restaurantSearchDTO.isIFoodDelivery(), userCityRestaurants);

        userCityRestaurants = filterByCardPaymentAcceptance(restaurantSearchDTO.getPaymentAcceptanceDTO().getCardsDTOs(), userCityRestaurants);

        SortSearch sortSearch = restaurantSearchDTO.getSortSearch();
        List<FilteredRestaurantDTO> orderedRestaurants = sortFilteredRestaurants(userCityRestaurants, sortSearch, new ArrayList<>());
        if (orderedRestaurants != null){
            return orderedRestaurants;
        } else {
            return userCityRestaurants;
        }


    }

    private List<FilteredRestaurantDTO> sortFilteredRestaurants(List<FilteredRestaurantDTO> userCityRestaurants, SortSearch sortSearch, List<FilteredRestaurantDTO> orderedRestaurants) {
        if(sortSearch != null) {
            if (sortSearch.equals(SortSearch.HIGHEST_RATED)) {
                //TODO Por ordem decrescente
                orderedRestaurants = userCityRestaurants.stream().sorted(Comparator.comparingDouble(restaurant -> repository.findById(restaurant.getRestaurantId()).get().getRating())).collect(Collectors.toList());
            }
            if (sortSearch.equals(SortSearch.SHORTEST_DELIVERY_FEE)) {
                orderedRestaurants = userCityRestaurants.stream().sorted(Comparator.comparingDouble(FilteredRestaurantDTO::getDeliveryFee)).collect(Collectors.toList());
            }
            if (sortSearch.equals(SortSearch.SHORTEST_DELIVERY_TIME)) {
                orderedRestaurants = userCityRestaurants.stream().sorted(Comparator.comparingDouble(FilteredRestaurantDTO::getDeliveryTime)).collect(Collectors.toList());
            }
            if (sortSearch.equals(SortSearch.SHORTEST_DELIVERY_PRICE)) {
                orderedRestaurants = userCityRestaurants.stream().sorted(Comparator.comparingDouble(restaurant -> repository.findById(restaurant.getRestaurantId()).get().getCost())).collect(Collectors.toList());
            }
            if (sortSearch.equals(SortSearch.SHORTEST_DISTANCE)) {
                orderedRestaurants = userCityRestaurants.stream().sorted(Comparator.comparingDouble(FilteredRestaurantDTO::getDistanceFromCustomer)).collect(Collectors.toList());
            }
            return orderedRestaurants;
        }
        return null;
    }

    private void fillFilteredRestaurantsWithDeliveryFee(Integer userId, List<FilteredRestaurantDTO> userCityRestaurants) {
        userCityRestaurants.forEach(userCityRestaurant -> {
            Double deliveryFee = distanceCalculatorService.calculateRestaurantDeliveryFeeBasedOnDistance(userId, userCityRestaurant.getRestaurantId(), userCityRestaurant);
            userCityRestaurant.setDeliveryFee(deliveryFee);
        });
    }

    private void fillFilteredRestaurantsWithDeliveryTime(Integer userId, List<FilteredRestaurantDTO> userCityRestaurants) {
        userCityRestaurants.forEach(userCityRestaurant -> {
            Double deliveryFee = distanceCalculatorService.calculateRestaurantDeliveryTimeBasedOnDistance(userId, userCityRestaurant.getRestaurantId(), userCityRestaurant);
            userCityRestaurant.setDeliveryTime(deliveryFee);
        });
    }

    private void fillFilteredRestaurantsWithDeliveryDistance(Integer userId, List<FilteredRestaurantDTO> userCityRestaurants) {
        userCityRestaurants.forEach(userCityRestaurant -> {
            Double deliveryFee = distanceCalculatorService.calculateRestaurantDefaultDeliveryRadius(userId, userCityRestaurant.getRestaurantId(), userCityRestaurant);
            userCityRestaurant.setDistanceFromCustomer(deliveryFee);
        });
    }

    private List<FilteredRestaurantDTO> filterByDefaultRadiusDistance(List<FilteredRestaurantDTO> userCityRestaurants) {
        return userCityRestaurants.stream().filter(userCityRestaurant -> userCityRestaurant.getDistanceFromCustomer() < DEFAULT_DELIVERY_RADIUS_IN_METERS).collect(Collectors.toList());
    }

    private List<FilteredRestaurantDTO> filterByDeliveryFee(Integer deliveryFeeFilter, List<FilteredRestaurantDTO> userCityRestaurants) {
        if (deliveryFeeFilter != null) {
            return userCityRestaurants.stream().filter(userCityRestaurant -> userCityRestaurant.getDeliveryFee() < deliveryFeeFilter).collect(Collectors.toList());
        } else {
            return userCityRestaurants;
        }
    }


    private List<FilteredRestaurantDTO> filterByDeliveryTime(Integer deliveryTimeFilter, List<FilteredRestaurantDTO> userCityRestaurants) {
        if (deliveryTimeFilter != null) {
            return userCityRestaurants.stream().filter(userCityRestaurant -> userCityRestaurant.getDeliveryTime() < deliveryTimeFilter).collect(Collectors.toList());
        } else {
            return userCityRestaurants;
        }
    }

    private List<FilteredRestaurantDTO> filterByCategory(List<RestaurantCategory> categoriesFilter, List<FilteredRestaurantDTO> userCityRestaurants) {
        if (isNotEmpty(categoriesFilter)) {
            return userCityRestaurants.stream().filter(userCityRestaurant -> categoriesFilter.stream()
                    .anyMatch(category -> userCityRestaurant.restaurantCategory.equals(category))).collect(Collectors.toList());
        } else {
            return userCityRestaurants;
        }
    }

    private List<FilteredRestaurantDTO> filterBySuperRestaurant(Boolean isSuperRestaurant, List<FilteredRestaurantDTO> userCityRestaurants) {
        if (isSuperRestaurant != null) {
            return userCityRestaurants.stream().filter(userCityRestaurant -> isSuperRestaurant == userCityRestaurant.isSuperRestaurant()).collect(Collectors.toList());
        } else {
            return userCityRestaurants;
        }
    }

    private List<FilteredRestaurantDTO> filterByTrackedDelivery(Boolean hasTrackedDelivery, List<FilteredRestaurantDTO> userCityRestaurants) {
        if (hasTrackedDelivery != null) {
            return userCityRestaurants.stream().filter(userCityRestaurant -> hasTrackedDelivery == userCityRestaurant.isHasTrackedDelivery()).collect(Collectors.toList());
        } else {
            return userCityRestaurants;
        }
    }

    private List<FilteredRestaurantDTO> filterByIFoodDelivery(Boolean isIFoodDelivery, List<FilteredRestaurantDTO> userCityRestaurants) {
        if (isIFoodDelivery != null) {
            return userCityRestaurants.stream().filter(userCityRestaurant -> isIFoodDelivery == userCityRestaurant.isIFoodDelivery()).collect(Collectors.toList());
        } else {
            return userCityRestaurants;
        }
    }

    private List<FilteredRestaurantDTO> filterByCashPaymentAcceptance(Boolean isCashPaymentAcceptance, List<FilteredRestaurantDTO> userCityRestaurants) {
        if (isCashPaymentAcceptance != null) {
            return userCityRestaurants.stream().filter(userCityRestaurant -> isCashPaymentAcceptance == userCityRestaurant.getPaymentAcceptanceDTO().isCash()).collect(Collectors.toList());
        } else {
            return userCityRestaurants;
        }
    }

    private List<FilteredRestaurantDTO> filterByPaycheckPaymentAcceptance(Boolean isPaycheckPaymentAcceptance, List<FilteredRestaurantDTO> userCityRestaurants) {
        if (isPaycheckPaymentAcceptance != null) {
            return userCityRestaurants.stream().filter(userCityRestaurant -> isPaycheckPaymentAcceptance == userCityRestaurant.getPaymentAcceptanceDTO().isPaycheck()).collect(Collectors.toList());
        } else {
            return userCityRestaurants;
        }
    }

    private List<FilteredRestaurantDTO> filterByOnlineTicketPaymentAcceptance(Boolean isOnlineTicket, List<FilteredRestaurantDTO> userCityRestaurants) {
        if (isOnlineTicket != null) {
            return userCityRestaurants.stream().filter(userCityRestaurant -> isOnlineTicket == userCityRestaurant.getPaymentAcceptanceDTO().isOnlineTicket()).collect(Collectors.toList());
        } else {
            return userCityRestaurants;
        }
    }

    private List<FilteredRestaurantDTO> filterByCardPaymentAcceptance(List<CardDTO> cards, List<FilteredRestaurantDTO> userCityRestaurants) {
        if (isNotEmpty(cards)) {
            return userCityRestaurants.stream().filter(userCityRestaurant -> cards.stream()
                    .anyMatch(cardDTO -> userCityRestaurant.getPaymentAcceptanceDTO().getCardsDTOs().stream()
                            .anyMatch(cardDTO1 -> cardDTO1.getCardType().equals(cardDTO.getCardType()) && cardDTO1.getFlag().equals(cardDTO1.getFlag())))).collect(Collectors.toList());
        } else {
            return userCityRestaurants;
        }
    }

}
