package com.levi.manager.listener;

import com.levi.manager.ManagerApplication;
import com.levi.manager.domain.Restaurant;
import com.levi.manager.dto.EvaluatedRestaurantDTO;
import com.levi.manager.service.RestaurantService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class)
@ActiveProfiles("test")
public class SuperRestaurantListenerTest {

    @InjectMocks
    private SuperRestaurantListener listener;

    @Mock
    private RestaurantService service;

    @Test
    public void ratingWasUpdated() {
        EvaluatedRestaurantDTO evaluatedRestaurantDTO = givenEvaluatedRestaurant();
        Restaurant restaurant = new Restaurant();
        listener.ratingWasUpdated(evaluatedRestaurantDTO, restaurant);
        Assert.assertEquals(restaurant.getIsSuperRestaurant(), true);
    }

    private EvaluatedRestaurantDTO givenEvaluatedRestaurant() {
        EvaluatedRestaurantDTO evaluatedRestaurantDTO = new EvaluatedRestaurantDTO();
        evaluatedRestaurantDTO.setSuperRestaurant(true);
        return evaluatedRestaurantDTO;
    }

}
