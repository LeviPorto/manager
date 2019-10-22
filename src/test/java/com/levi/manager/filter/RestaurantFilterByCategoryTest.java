package com.levi.manager.filter;

import com.levi.manager.ManagerApplication;
import com.levi.manager.domain.enumeration.RestaurantCategory;
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
public class RestaurantFilterByCategoryTest {

    @InjectMocks
    private RestaurantFilterByCategory filter;

    private final Integer FIRST_RESTAURANT = 0;

    private final Integer FIRST_RESTAURANT_ID = 1;
    private final Double FIRST_RESTAURANT_LATITUDE = 15.0;
    private final Double FIRST_RESTAURANT_LONGITUDE = 20.0;

    private final Integer SECOND_RESTAURANT_ID = 2;
    private final Double SECOND_RESTAURANT_LATITUDE = 15.0;
    private final Double SECOND_RESTAURANT_LONGITUDE = 25.0;

    @Test
    public void filterRestaurantSearchingWithoutCategory() {
        List<FilteredRestaurantDTO> beginFilteredRestaurants = givenFilteredRestaurants();
        List<FilteredRestaurantDTO> filteredRestaurants = filter.filterRestaurant(new RestaurantSearchDTO(), beginFilteredRestaurants);
        Assert.assertEquals(filteredRestaurants, beginFilteredRestaurants);
    }

    @Test
    public void filterRestaurantSearchingWithCategory() {
        List<FilteredRestaurantDTO> beginFilteredRestaurants = givenFilteredRestaurants();
        List<FilteredRestaurantDTO> filteredRestaurants = filter.filterRestaurant(givenRestaurantSearchDTO(), beginFilteredRestaurants);
        Assert.assertEquals(filteredRestaurants.get(FIRST_RESTAURANT).getRestaurantCategory(), RestaurantCategory.ACAI);
    }

    private List<FilteredRestaurantDTO> givenFilteredRestaurants() {
        FilteredRestaurantDTO firstFilteredRestaurant = new FilteredRestaurantDTO();
        firstFilteredRestaurant.setRestaurantCategory(RestaurantCategory.ACAI);
        firstFilteredRestaurant.setRestaurantId(FIRST_RESTAURANT_ID);
        firstFilteredRestaurant.setLatitude(FIRST_RESTAURANT_LATITUDE);
        firstFilteredRestaurant.setLongitude(FIRST_RESTAURANT_LONGITUDE);
        FilteredRestaurantDTO secondFilteredRestaurant = new FilteredRestaurantDTO();
        secondFilteredRestaurant.setRestaurantId(SECOND_RESTAURANT_ID);
        secondFilteredRestaurant.setLatitude(SECOND_RESTAURANT_LATITUDE);
        secondFilteredRestaurant.setLongitude(SECOND_RESTAURANT_LONGITUDE);
        secondFilteredRestaurant.setRestaurantCategory(RestaurantCategory.CANDY_AND_CAKE);
        return Arrays.asList(firstFilteredRestaurant, secondFilteredRestaurant);
    }

    private RestaurantSearchDTO givenRestaurantSearchDTO() {
        RestaurantSearchDTO restaurantSearchDTO = new RestaurantSearchDTO();
        restaurantSearchDTO.setCategories(Arrays.asList(RestaurantCategory.ACAI, RestaurantCategory.BRAZILIAN_FOOD));
        return restaurantSearchDTO;
    }

}
