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
public class RestaurantFilterByDefaultRadiusDistanceTest {

    @InjectMocks
    private RestaurantFilterByDefaultRadiusDistance filter;

    private final Integer FIRST_RESTAURANT_ID = 1;
    private final Double FIRST_RESTAURANT_LATITUDE = 15.0;
    private final Double FIRST_RESTAURANT_LONGITUDE = 20.0;
    private final Double FIRST_RESTAURANT_DISTANCE = 200.0;

    private final Integer SECOND_RESTAURANT_ID = 2;
    private final Double SECOND_RESTAURANT_LATITUDE = 15.0;
    private final Double SECOND_RESTAURANT_LONGITUDE = 25.0;
    private final Double SECOND_RESTAURANT_DISTANCE = 3000.0;

    private final Integer FIRST_RESTAURANT = 0;

    private final Double DELTA = 1e-15;

    @Test
    public void filterRestaurant() {
        List<FilteredRestaurantDTO> filteredRestaurants = filter.filterRestaurant(new RestaurantSearchDTO(), givenFilteredRestaurants());
        Assert.assertEquals(filteredRestaurants.get(FIRST_RESTAURANT).getDistanceFromCustomer(), 200.0, DELTA);
    }

    private List<FilteredRestaurantDTO> givenFilteredRestaurants() {
        FilteredRestaurantDTO firstFilteredRestaurant = new FilteredRestaurantDTO();
        firstFilteredRestaurant.setRestaurantId(FIRST_RESTAURANT_ID);
        firstFilteredRestaurant.setLatitude(FIRST_RESTAURANT_LATITUDE);
        firstFilteredRestaurant.setLongitude(FIRST_RESTAURANT_LONGITUDE);
        firstFilteredRestaurant.setDistanceFromCustomer(FIRST_RESTAURANT_DISTANCE);
        FilteredRestaurantDTO secondFilteredRestaurant = new FilteredRestaurantDTO();
        secondFilteredRestaurant.setRestaurantId(SECOND_RESTAURANT_ID);
        secondFilteredRestaurant.setLatitude(SECOND_RESTAURANT_LATITUDE);
        secondFilteredRestaurant.setLongitude(SECOND_RESTAURANT_LONGITUDE);
        secondFilteredRestaurant.setDistanceFromCustomer(SECOND_RESTAURANT_DISTANCE);
        return Arrays.asList(firstFilteredRestaurant, secondFilteredRestaurant);
    }

}
