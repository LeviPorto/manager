package com.levi.manager.service.nontransactional;

import com.levi.manager.ManagerApplication;
import com.levi.manager.domain.User;
import com.levi.manager.dto.FilteredRestaurantDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

//TODO Rever mock de metodos estaticos e fz com que esse teste mocke o DistanceCalculatorUtil
//TODO Fazer testes pro abstract

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class)
@ActiveProfiles("test")
public class DistanceCalculatorServiceTest {

    @Autowired
    private DistanceCalculatorService distanceCalculatorService;

    private final Double USER_LATITUDE = 20.0;
    private final Double USER_LONGITUDE = 15.0;
    private final Double FILTERED_RESTAURANT_LATITUDE = 20.0;
    private final Double FILTERED_RESTAURANT_LONGITUDE = 12.0;
    private final Double FILTERED_RESTAURANT_DELIVERY_FEE = 0.02;

    private final Double DELTA = 1e-15;

    @Test
    public void calculateRestaurantDefaultDeliveryRadius() {
        FilteredRestaurantDTO filteredRestaurantDTO = givenFilteredRestaurantDTO();
        User user = givenUser();
        distanceCalculatorService.calculateRestaurantDefaultDeliveryRadius(user, filteredRestaurantDTO);
        Assert.assertEquals(filteredRestaurantDTO.getDistanceFromCustomer(), 313.46296639187625, DELTA);
    }

    @Test
    public void calculateRestaurantDeliveryFeeBasedOnDistance() {
        FilteredRestaurantDTO filteredRestaurantDTO = givenFilteredRestaurantDTO();
        User user = givenUser();
        distanceCalculatorService.calculateRestaurantDeliveryFeeBasedOnDistance(user, filteredRestaurantDTO);
        Assert.assertEquals(filteredRestaurantDTO.getDeliveryFee(), 6.269259327837525, DELTA);
    }

    @Test
    public void calculateRestaurantDeliveryTimeBasedOnDistance() {
        FilteredRestaurantDTO filteredRestaurantDTO = givenFilteredRestaurantDTO();
        User user = givenUser();
        distanceCalculatorService.calculateRestaurantDeliveryTimeBasedOnDistance(user, filteredRestaurantDTO);
        Assert.assertEquals(filteredRestaurantDTO.getDeliveryTime(), 37.630608210309276, DELTA);
    }

    private User givenUser() {
        User user = new User();
        user.setLatitude(USER_LATITUDE);
        user.setLongitude(USER_LONGITUDE);
        return user;
    }

    private FilteredRestaurantDTO givenFilteredRestaurantDTO() {
        FilteredRestaurantDTO filteredRestaurantDTO = new FilteredRestaurantDTO();
        filteredRestaurantDTO.setLatitude(FILTERED_RESTAURANT_LATITUDE);
        filteredRestaurantDTO.setLongitude(FILTERED_RESTAURANT_LONGITUDE);
        filteredRestaurantDTO.setRestaurantDeliveryFee(FILTERED_RESTAURANT_DELIVERY_FEE);
        return filteredRestaurantDTO;
    }

}
