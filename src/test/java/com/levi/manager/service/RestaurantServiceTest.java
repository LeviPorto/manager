package com.levi.manager.service;

import com.levi.manager.ManagerApplication;
import com.levi.manager.dao.RestaurantDao;
import com.levi.manager.domain.User;
import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import com.levi.manager.dto.enumeration.SortSearch;
import com.levi.manager.filter.RestaurantFilter;
import com.levi.manager.repository.RestaurantRepository;
import com.levi.manager.service.nontransactional.DistanceCalculatorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
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
    private final Double DELIVERY_FEE = 0.02;
    private final Double DELIVERY_RADIUS = 200.0;
    private final Double DELIVERY_TIME = 20.0;
    private final Integer FIRST_RESTAURANT_ID = 1;
    private final Double FIRST_RESTAURANT_LATITUDE = 20.0;
    private final Double FIRST_RESTAURANT_LONGITUDE = 15.0;
    private final Integer SECOND_RESTAURANT_ID = 2;
    private final Double SECOND_RESTAURANT_LATITUDE = 15.0;
    private final Double SECOND_RESTAURANT_LONGITUDE = 15.0;


    @Test
    public void retrieveFilteredRestaurantsWithoutSearchedName() {
        RestaurantSearchDTO restaurantSearchDTO = givenRestaurantSearchDTO();
        BDDMockito.given(dao.findUserCityRestaurants(restaurantSearchDTO)).willReturn(givenFilteredRestaurants());
        BDDMockito.given(userService.retrieveById(restaurantSearchDTO.getUserId())).willReturn(givenUser());
        BDDMockito.given(filters.isEmpty()).willReturn(true);
        BDDMockito.given(distanceCalculatorService.calculateRestaurantDeliveryFeeBasedOnDistance(givenUser(), givenFilteredRestaurant())).willReturn(DELIVERY_FEE);
        BDDMockito.given(distanceCalculatorService.calculateRestaurantDefaultDeliveryRadius(givenUser(), givenFilteredRestaurant())).willReturn(DELIVERY_RADIUS);
        BDDMockito.given(distanceCalculatorService.calculateRestaurantDeliveryTimeBasedOnDistance(givenUser(), givenFilteredRestaurant())).willReturn(DELIVERY_TIME);

        List<FilteredRestaurantDTO> filteredRestaurantsDTO = service.retrieveFilteredRestaurants(givenRestaurantSearchDTO());
        Assert.assertEquals(filteredRestaurantsDTO, givenFilteredRestaurants());
    }

    private RestaurantSearchDTO givenRestaurantSearchDTO() {
        RestaurantSearchDTO restaurantSearchDTO = new RestaurantSearchDTO();
        restaurantSearchDTO.setUserCity(USER_CITY);
        restaurantSearchDTO.setUserId(USER_ID);
        restaurantSearchDTO.setSortSearch(SortSearch.DEFAULT);
        return restaurantSearchDTO;
    }

    private User givenUser() {
        User user = new User();
        user.setId(USER_ID);
        return user;
    }

    private List<FilteredRestaurantDTO> givenFilteredRestaurants() {
        FilteredRestaurantDTO firstFilteredRestaurantDTO = new FilteredRestaurantDTO();
        firstFilteredRestaurantDTO.setRestaurantId(FIRST_RESTAURANT_ID);
        firstFilteredRestaurantDTO.setLatitude(FIRST_RESTAURANT_LATITUDE);
        firstFilteredRestaurantDTO.setLongitude(FIRST_RESTAURANT_LONGITUDE);
        FilteredRestaurantDTO secondFilteredRestaurantDTO = new FilteredRestaurantDTO();
        secondFilteredRestaurantDTO.setRestaurantId(SECOND_RESTAURANT_ID);
        secondFilteredRestaurantDTO.setLatitude(SECOND_RESTAURANT_LATITUDE);
        secondFilteredRestaurantDTO.setLongitude(SECOND_RESTAURANT_LONGITUDE);
        return Arrays.asList(firstFilteredRestaurantDTO, secondFilteredRestaurantDTO);
    }

    private FilteredRestaurantDTO givenFilteredRestaurant() {
        FilteredRestaurantDTO filteredRestaurantDTO = new FilteredRestaurantDTO();
        return filteredRestaurantDTO;
    }

}
