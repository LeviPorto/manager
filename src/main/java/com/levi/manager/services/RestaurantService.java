package com.levi.manager.services;

import com.levi.manager.daos.RestaurantDao;
import com.levi.manager.dtos.CardDTO;
import com.levi.manager.dtos.RestaurantFilteredDTO;
import com.levi.manager.dtos.RestaurantSearchDTO;
import com.levi.manager.dtos.enuns.SortSearch;
import com.levi.manager.entities.Restaurant;
import com.levi.manager.entities.User;
import com.levi.manager.entities.enuns.RestaurantCategory;
import com.levi.manager.repositories.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.levi.manager.services.DistanceCalculatorService.DEFAULT_DELIVERY_RADIUS_IN_METERS;
import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;

//TODO Melhorar o retrieve de restaurantes
//TODO Verificar se cabe por uma interface de filtros
//TODO Extrair o loop tendo o distanceCalculator dentro

//TODO Melhorar os Null Pointer Exception nos filtros de Payment Acceptance

//TODO Fazer um filtro só. Cada método retornará um booleano, no qual será aculmulado e passado nesse único filtro

//TODO Dao só vai servir para trazer os restaurantes da cidade, com a projeção necessária para exibição e calculo. Service vai fazer os filtors customizados

//TODO Limpar o envio de entidades desnecessários no RestaurantFilteredDTO

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

        List<RestaurantFilteredDTO> userCityRestaurants = dao.findUserCityRestaurants(restaurantSearchDTO);

        userCityRestaurants = filterByDefaultRadiusDistance(restaurantSearchDTO.getUserId(), userCityRestaurants);

        userCityRestaurants = filterByCategory(restaurantSearchDTO.getCategories(), userCityRestaurants);
        userCityRestaurants = filterByDeliveryFee(restaurantSearchDTO.getDeliveryFee(), restaurantSearchDTO.getUserId(), userCityRestaurants);
        userCityRestaurants = filterByDeliveryTime(restaurantSearchDTO.getDeliveryTime(), restaurantSearchDTO.getUserId(), userCityRestaurants);
        userCityRestaurants = filterBySuperRestaurant(restaurantSearchDTO.getIsSuperRestaurant(), userCityRestaurants);
        userCityRestaurants = filterByTrackedDelivery(restaurantSearchDTO.getHasTrackedDelivery(), userCityRestaurants);
        userCityRestaurants = filterByIFoodDelivery(restaurantSearchDTO.getIsIFoodDelivery(), userCityRestaurants);

        userCityRestaurants = filterByCashPaymentAcceptance(restaurantSearchDTO.getPaymentAcceptanceDTO().getCash(), userCityRestaurants);
        userCityRestaurants = filterByPaycheckPaymentAcceptance(restaurantSearchDTO.getPaymentAcceptanceDTO().getPaycheck(), userCityRestaurants);
        userCityRestaurants = filterByOnlineTicketPaymentAcceptance(restaurantSearchDTO.getIsIFoodDelivery(), userCityRestaurants);

        userCityRestaurants = filterByCardPaymentAcceptance(restaurantSearchDTO.getPaymentAcceptanceDTO().getCardsDTOs(), userCityRestaurants);

        SortSearch sortSearch = restaurantSearchDTO.getSortSearch();
        List<RestaurantFilteredDTO> orderedRestaurants = sortFilteredRestaurants(userCityRestaurants, sortSearch, new ArrayList<>());
        if (orderedRestaurants != null){
            return orderedRestaurants;
        } else {
            return userCityRestaurants;
        }


    }

    private List<RestaurantFilteredDTO> sortFilteredRestaurants(List<RestaurantFilteredDTO> userCityRestaurants, SortSearch sortSearch, List<RestaurantFilteredDTO> orderedRestaurants) {
        if(sortSearch != null) {
            if (sortSearch.equals(SortSearch.HIGHEST_RATED)) {
                //TODO Por ordem decrescente
                orderedRestaurants = userCityRestaurants.stream().sorted(Comparator.comparingDouble(restaurant -> repository.findById(restaurant.getRestaurantId()).get().getRate())).collect(Collectors.toList());
            }
            if (sortSearch.equals(SortSearch.SHORTEST_DELIVERY_FEE)) {
                orderedRestaurants = userCityRestaurants.stream().sorted(Comparator.comparingDouble(RestaurantFilteredDTO::getDeliveryFee)).collect(Collectors.toList());
            }
            if (sortSearch.equals(SortSearch.SHORTEST_DELIVERY_TIME)) {
                orderedRestaurants = userCityRestaurants.stream().sorted(Comparator.comparingDouble(RestaurantFilteredDTO::getDeliveryTime)).collect(Collectors.toList());
            }
            if (sortSearch.equals(SortSearch.SHORTEST_DELIVERY_PRICE)) {
                orderedRestaurants = userCityRestaurants.stream().sorted(Comparator.comparingDouble(restaurant -> repository.findById(restaurant.getRestaurantId()).get().getCost())).collect(Collectors.toList());
            }
            if (sortSearch.equals(SortSearch.SHORTEST_DISTANCE)) {
                orderedRestaurants = userCityRestaurants.stream().sorted(Comparator.comparingDouble(RestaurantFilteredDTO::getDistanceFromCustomer)).collect(Collectors.toList());
            }
            return orderedRestaurants;
        }
        return null;
    }

    private List<RestaurantFilteredDTO> filterByDefaultRadiusDistance(Integer userId, List<RestaurantFilteredDTO> orderedUserCityRestaurants) {
        return orderedUserCityRestaurants.stream().filter(userCityRestaurant -> distanceCalculatorService
                    .calculateRestaurantDefaultDeliveryRadius(userId, userCityRestaurant.getRestaurantId(), userCityRestaurant) < DEFAULT_DELIVERY_RADIUS_IN_METERS).collect(Collectors.toList());
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
                    .calculateRestaurantDeliveryFeeBasedOnDistance(userId, userCityRestaurant.getRestaurantId(), userCityRestaurant) < deliveryFeeFilter).collect(Collectors.toList());
        } else {
            return orderedUserCityRestaurants;
        }
    }


    private List<RestaurantFilteredDTO> filterByDeliveryTime(Integer deliveryTimeFilter, Integer userId, List<RestaurantFilteredDTO> orderedUserCityRestaurants) {
        if (deliveryTimeFilter != null) {
            return orderedUserCityRestaurants.stream().filter(userCityRestaurant -> distanceCalculatorService
                    .calculateRestaurantDeliveryTimeBasedOnDistance(userId, userCityRestaurant.getRestaurantId(), userCityRestaurant) < deliveryTimeFilter).collect(Collectors.toList());
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
            return orderedUserCityRestaurants.stream().filter(userCityRestaurant -> hasTrackedDelivery == userCityRestaurant.getHasTrackedDelivery()).collect(Collectors.toList());
        } else {
            return orderedUserCityRestaurants;
        }
    }

    private List<RestaurantFilteredDTO> filterByIFoodDelivery(Boolean isIFoodDelivery, List<RestaurantFilteredDTO> orderedUserCityRestaurants) {
        if (isIFoodDelivery != null) {
            return orderedUserCityRestaurants.stream().filter(userCityRestaurant -> isIFoodDelivery == userCityRestaurant.getIsIFoodDelivery()).collect(Collectors.toList());
        } else {
            return orderedUserCityRestaurants;
        }
    }

    private List<RestaurantFilteredDTO> filterByCashPaymentAcceptance(Boolean isCashPaymentAcceptance, List<RestaurantFilteredDTO> orderedUserCityRestaurants) {
        if (isCashPaymentAcceptance != null) {
            return orderedUserCityRestaurants.stream().filter(userCityRestaurant -> isCashPaymentAcceptance == userCityRestaurant.getPaymentAcceptanceDTO().getCash()).collect(Collectors.toList());
        } else {
            return orderedUserCityRestaurants;
        }
    }

    private List<RestaurantFilteredDTO> filterByPaycheckPaymentAcceptance(Boolean isPaycheckPaymentAcceptance, List<RestaurantFilteredDTO> orderedUserCityRestaurants) {
        if (isPaycheckPaymentAcceptance != null) {
            return orderedUserCityRestaurants.stream().filter(userCityRestaurant -> isPaycheckPaymentAcceptance == userCityRestaurant.getPaymentAcceptanceDTO().getPaycheck()).collect(Collectors.toList());
        } else {
            return orderedUserCityRestaurants;
        }
    }

    private List<RestaurantFilteredDTO> filterByOnlineTicketPaymentAcceptance(Boolean isOnlineTicket, List<RestaurantFilteredDTO> orderedUserCityRestaurants) {
        if (isOnlineTicket != null) {
            return orderedUserCityRestaurants.stream().filter(userCityRestaurant -> isOnlineTicket == userCityRestaurant.getPaymentAcceptanceDTO().getOnlineTicket()).collect(Collectors.toList());
        } else {
            return orderedUserCityRestaurants;
        }
    }

    private List<RestaurantFilteredDTO> filterByCardPaymentAcceptance(List<CardDTO> cards, List<RestaurantFilteredDTO> orderedUserCityRestaurants) {
        if (isNotEmpty(cards)) {
            return orderedUserCityRestaurants.stream().filter(userCityRestaurant -> cards.stream()
                    .anyMatch(cardDTO -> userCityRestaurant.getPaymentAcceptanceDTO().getCardsDTOs().stream()
                            .anyMatch(cardDTO1 -> cardDTO1.getCardType().equals(cardDTO.getCardType()) && cardDTO1.getFlag().equals(cardDTO1.getFlag())))).collect(Collectors.toList());
        } else {
            return orderedUserCityRestaurants;
        }
    }

}
