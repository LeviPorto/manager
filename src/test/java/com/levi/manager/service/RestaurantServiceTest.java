package com.levi.manager.service;

import com.levi.manager.ManagerApplication;
import com.levi.manager.dao.RestaurantDao;
import com.levi.manager.domain.DeliveryMan;
import com.levi.manager.domain.Restaurant;
import com.levi.manager.domain.User;
import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import com.levi.manager.dto.enumeration.SortSearch;
import com.levi.manager.filter.RestaurantFilter;
import com.levi.manager.repository.RestaurantRepository;
import com.levi.manager.service.nontransactional.DistanceCalculatorService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class)
@ActiveProfiles("test")
public class RestaurantServiceTest {

    @InjectMocks
    private RestaurantService service;

    @Mock
    private RestaurantDao dao;

    @Mock
    private RestaurantRepository repository;

    @Mock
    private List<RestaurantFilter> filters;

    @Mock
    private UserService userService;

    @Mock
    private DistanceCalculatorService distanceCalculatorService;

    private final Integer USER_ID = 1;
    private final String USER_CITY = "City 1";

    private final Integer RESTAURANT_ID = 1;
    private final Double RESTAURANT_LATITUDE = 20.0;
    private final Double RESTAURANT_LONGITUDE = 15.0;
    private final Integer SECOND_RESTAURANT_ID = 2;
    private final Double SECOND_RESTAURANT_LATITUDE = 15.0;
    private final Double SECOND_RESTAURANT_LONGITUDE = 15.0;

    private final Double RATING = 5.0;
    private final Double SECOND_RATING = 3.0;

    private final Double DELIVERY_FEE = 20.0;
    private final Double SECOND_DELIVERY_FEE = 15.0;

    private final Double DISTANCE = 200.0;
    private final Double SECOND_DISTANCE = 150.0;

    private final Double DELIVERY_TIME = 30.0;
    private final Double SECOND_DELIVERY_TIME = 20.0;

    private final Integer RESTAURANT = 0;
    private final Integer SECOND_RESTAURANT = 1;

    private final Integer DELIVERY_MAN_ID = 1;

    private final Double DELTA = 1e-15;

    @Before
    public void setUp() {
        BDDMockito.given(filters.isEmpty()).willReturn(true);
    }

    @Test
    public void retrieveDefaultOrderedFilteredRestaurants() {
        RestaurantSearchDTO restaurantSearchDTO = givenDefaultOrderedRestaurantSearchDTO();
        List<FilteredRestaurantDTO> filteredRestaurants = givenFilteredRestaurants();
        User user = givenUser();
        whenFilterRestaurantsByUserCity(restaurantSearchDTO, filteredRestaurants, user);
        List<FilteredRestaurantDTO> filteredRestaurantsDTO = service.retrieveFilteredRestaurants(restaurantSearchDTO);
        Assert.assertEquals(filteredRestaurantsDTO, filteredRestaurants);
    }

    @Test
    public void retrieveHighestRatingOrderedFilteredRestaurants() {
        RestaurantSearchDTO restaurantSearchDTO = givenHighestRatingRestaurantSearchDTO();
        List<FilteredRestaurantDTO> filteredRestaurants = givenFilteredRestaurants();
        filteredRestaurants.get(RESTAURANT).setRating(SECOND_RATING);
        filteredRestaurants.get(SECOND_RESTAURANT).setRating(RATING);
        BDDMockito.given(dao.findUserCityRestaurants(restaurantSearchDTO)).willReturn(filteredRestaurants);
        List<FilteredRestaurantDTO> filteredRestaurantsDTO = service.retrieveFilteredRestaurants(restaurantSearchDTO);
        Assert.assertEquals(filteredRestaurantsDTO.get(RESTAURANT).getRating(), RATING, DELTA);
    }

    @Test
    public void retrieveShortestDeliveryFeeOrderedFilteredRestaurants() {
        RestaurantSearchDTO restaurantSearchDTO = givenShortestDeliveryRestaurantSearchDTO();
        User user = givenUser();
        List<FilteredRestaurantDTO> filteredRestaurants = givenFilteredRestaurants();
        whenFilterRestaurantsByUserCity(restaurantSearchDTO, filteredRestaurants, user);
        BDDMockito.given(distanceCalculatorService.calculateRestaurantDeliveryFeeBasedOnDistance(user, filteredRestaurants.get(RESTAURANT))).willReturn(DELIVERY_FEE);
        BDDMockito.given(distanceCalculatorService.calculateRestaurantDeliveryFeeBasedOnDistance(user, filteredRestaurants.get(SECOND_RESTAURANT))).willReturn(SECOND_DELIVERY_FEE);
        List<FilteredRestaurantDTO> filteredRestaurantsDTO = service.retrieveFilteredRestaurants(restaurantSearchDTO);
        Assert.assertEquals(filteredRestaurantsDTO.get(RESTAURANT).getDeliveryFee(), SECOND_DELIVERY_FEE, DELTA);
    }

    @Test
    public void retrieveShortestDistanceOrderedFilteredRestaurants() {
        RestaurantSearchDTO restaurantSearchDTO = givenShortestDistanceRestaurantSearchDTO();
        User user = givenUser();
        List<FilteredRestaurantDTO> filteredRestaurants = givenFilteredRestaurants();
        whenFilterRestaurantsByUserCity(restaurantSearchDTO, filteredRestaurants, user);
        BDDMockito.given(distanceCalculatorService.calculateRestaurantDefaultDeliveryRadius(user, filteredRestaurants.get(RESTAURANT))).willReturn(DISTANCE);
        BDDMockito.given(distanceCalculatorService.calculateRestaurantDefaultDeliveryRadius(user, filteredRestaurants.get(SECOND_RESTAURANT))).willReturn(SECOND_DISTANCE);
        List<FilteredRestaurantDTO> filteredRestaurantsDTO = service.retrieveFilteredRestaurants(restaurantSearchDTO);
        Assert.assertEquals(filteredRestaurantsDTO.get(RESTAURANT).getDistanceFromCustomer(), SECOND_DISTANCE, DELTA);
    }

    @Test
    public void retrieveShortestDeliveryTimeOrderedFilteredRestaurants() {
        RestaurantSearchDTO restaurantSearchDTO = givenShortestDeliveryTimeRestaurantSearchDTO();
        User user = givenUser();
        List<FilteredRestaurantDTO> filteredRestaurants = givenFilteredRestaurants();
        whenFilterRestaurantsByUserCity(restaurantSearchDTO, filteredRestaurants, user);
        BDDMockito.given(distanceCalculatorService.calculateRestaurantDeliveryTimeBasedOnDistance(user, filteredRestaurants.get(RESTAURANT))).willReturn(DELIVERY_TIME);
        BDDMockito.given(distanceCalculatorService.calculateRestaurantDeliveryTimeBasedOnDistance(user, filteredRestaurants.get(SECOND_RESTAURANT))).willReturn(SECOND_DELIVERY_TIME);
        List<FilteredRestaurantDTO> filteredRestaurantsDTO = service.retrieveFilteredRestaurants(restaurantSearchDTO);
        Assert.assertEquals(filteredRestaurantsDTO.get(RESTAURANT).getDeliveryTime(), SECOND_DELIVERY_TIME, DELTA);
    }

    @Test
    public void retrieveByDeliveryMan() {
        BDDMockito.given(repository.findByDeliveryManId(DELIVERY_MAN_ID)).willReturn(givenRestaurant());
        Restaurant restaurant = service.retrieveByDeliveryMan(DELIVERY_MAN_ID);
        Assert.assertNotNull(restaurant);
    }

    private RestaurantSearchDTO givenDefaultOrderedRestaurantSearchDTO() {
        RestaurantSearchDTO restaurantSearchDTO = givenRestaurantSearchDTO();
        restaurantSearchDTO.setSortSearch(SortSearch.DEFAULT);
        return restaurantSearchDTO;
    }

    private void whenFilterRestaurantsByUserCity(RestaurantSearchDTO restaurantSearchDTO, List<FilteredRestaurantDTO> filteredRestaurants, User user) {
        BDDMockito.given(dao.findUserCityRestaurants(restaurantSearchDTO)).willReturn(filteredRestaurants);
        BDDMockito.given(userService.retrieveById(restaurantSearchDTO.getUserId())).willReturn(user);
    }

    private RestaurantSearchDTO givenHighestRatingRestaurantSearchDTO() {
        RestaurantSearchDTO restaurantSearchDTO = givenRestaurantSearchDTO();
        restaurantSearchDTO.setSortSearch(SortSearch.HIGHEST_RATED);
        return restaurantSearchDTO;
    }

    private RestaurantSearchDTO givenShortestDeliveryRestaurantSearchDTO() {
        RestaurantSearchDTO restaurantSearchDTO = givenRestaurantSearchDTO();
        restaurantSearchDTO.setSortSearch(SortSearch.SHORTEST_DELIVERY_FEE);
        return restaurantSearchDTO;
    }

    private RestaurantSearchDTO givenShortestDistanceRestaurantSearchDTO() {
        RestaurantSearchDTO restaurantSearchDTO = givenRestaurantSearchDTO();
        restaurantSearchDTO.setSortSearch(SortSearch.SHORTEST_DISTANCE);
        return restaurantSearchDTO;
    }

    private RestaurantSearchDTO givenShortestDeliveryTimeRestaurantSearchDTO() {
        RestaurantSearchDTO restaurantSearchDTO = givenRestaurantSearchDTO();
        restaurantSearchDTO.setSortSearch(SortSearch.SHORTEST_DELIVERY_TIME);
        return restaurantSearchDTO;
    }

    private Restaurant givenRestaurant() {
        Restaurant restaurant = new Restaurant();
        DeliveryMan deliveryMan = new DeliveryMan();
        deliveryMan.setId(DELIVERY_MAN_ID);
        restaurant.setDeliveryMan(Collections.singletonList(deliveryMan));
        return restaurant;
    }

    private RestaurantSearchDTO givenRestaurantSearchDTO() {
        RestaurantSearchDTO restaurantSearchDTO = new RestaurantSearchDTO();
        restaurantSearchDTO.setUserCity(USER_CITY);
        restaurantSearchDTO.setUserId(USER_ID);
        return restaurantSearchDTO;
    }

    private User givenUser() {
        User user = new User();
        user.setId(USER_ID);
        return user;
    }

    private List<FilteredRestaurantDTO> givenFilteredRestaurants() {
        FilteredRestaurantDTO firstFilteredRestaurantDTO = new FilteredRestaurantDTO();
        firstFilteredRestaurantDTO.setRestaurantId(RESTAURANT_ID);
        firstFilteredRestaurantDTO.setLatitude(RESTAURANT_LATITUDE);
        firstFilteredRestaurantDTO.setLongitude(RESTAURANT_LONGITUDE);
        FilteredRestaurantDTO secondFilteredRestaurantDTO = new FilteredRestaurantDTO();
        secondFilteredRestaurantDTO.setRestaurantId(SECOND_RESTAURANT_ID);
        secondFilteredRestaurantDTO.setLatitude(SECOND_RESTAURANT_LATITUDE);
        secondFilteredRestaurantDTO.setLongitude(SECOND_RESTAURANT_LONGITUDE);
        return Arrays.asList(firstFilteredRestaurantDTO, secondFilteredRestaurantDTO);
    }

}
