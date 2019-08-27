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

        //TODO por calculo da distância aqui para reduzir as iterações

        orderedUserCityRestaurants = filterByDeliveryFee(restaurantSearchDTO.getDeliveryFee(), restaurantSearchDTO.getUserId(), orderedUserCityRestaurants);
        orderedUserCityRestaurants = filterByCategory(restaurantSearchDTO.getCategories(), orderedUserCityRestaurants);

        if (restaurantSearchDTO.getPaymentAcceptanceDTO() != null) {

        }

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

}
