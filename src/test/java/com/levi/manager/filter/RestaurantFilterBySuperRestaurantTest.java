package com.levi.manager.filter;

import com.levi.manager.ManagerApplication;
import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class)
@ActiveProfiles("test")
public class RestaurantFilterBySuperRestaurantTest {

    @InjectMocks
    private RestaurantFilterBySuperRestaurant filter;

    private final Integer FIRST_RESTAURANT = 0;

    private final Integer FIRST_RESTAURANT_ID = 1;
    private final Double FIRST_RESTAURANT_LATITUDE = 15.0;
    private final Double FIRST_RESTAURANT_LONGITUDE = 20.0;

    private final Integer SECOND_RESTAURANT_ID = 2;
    private final Double SECOND_RESTAURANT_LATITUDE = 15.0;
    private final Double SECOND_RESTAURANT_LONGITUDE = 25.0;

    @Test
    public void filterRestaurantSearchingWithoutSuperRestaurant() {
        List<FilteredRestaurantDTO> beginFilteredRestaurants = givenFilteredRestaurants();
        List<FilteredRestaurantDTO> filteredRestaurants = filter.filterRestaurant(new RestaurantSearchDTO(), beginFilteredRestaurants);
        Assert.assertEquals(filteredRestaurants, beginFilteredRestaurants);
    }

    @Test
    public void filterRestaurantSearchingWithSuperRestaurant() {
        List<FilteredRestaurantDTO> beginFilteredRestaurants = givenFilteredRestaurants();
        List<FilteredRestaurantDTO> filteredRestaurants = filter.filterRestaurant(givenRestaurantSearchDTO(), beginFilteredRestaurants);
        Assert.assertTrue(filteredRestaurants.get(FIRST_RESTAURANT).isSuperRestaurant());
    }

    private List<FilteredRestaurantDTO> givenFilteredRestaurants() {
        FilteredRestaurantDTO firstFilteredRestaurant = new FilteredRestaurantDTO();
        firstFilteredRestaurant.setRestaurantId(FIRST_RESTAURANT_ID);
        firstFilteredRestaurant.setLatitude(FIRST_RESTAURANT_LATITUDE);
        firstFilteredRestaurant.setLongitude(FIRST_RESTAURANT_LONGITUDE);
        firstFilteredRestaurant.setSuperRestaurant(true);
        FilteredRestaurantDTO secondFilteredRestaurant = new FilteredRestaurantDTO();
        secondFilteredRestaurant.setRestaurantId(SECOND_RESTAURANT_ID);
        secondFilteredRestaurant.setLatitude(SECOND_RESTAURANT_LATITUDE);
        secondFilteredRestaurant.setLongitude(SECOND_RESTAURANT_LONGITUDE);
        secondFilteredRestaurant.setSuperRestaurant(true);
        return Arrays.asList(firstFilteredRestaurant, secondFilteredRestaurant);
    }

    private RestaurantSearchDTO givenRestaurantSearchDTO() {
        RestaurantSearchDTO restaurantSearchDTO = new RestaurantSearchDTO();
        restaurantSearchDTO.setSuperRestaurant(true);
        return restaurantSearchDTO;
    }

}
