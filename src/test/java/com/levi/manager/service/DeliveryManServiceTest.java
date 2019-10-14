package com.levi.manager.service;

import com.levi.manager.ManagerApplication;
import com.levi.manager.domain.DeliveryMan;
import com.levi.manager.domain.Restaurant;
import com.levi.manager.domain.enumeration.Occupation;
import com.levi.manager.repository.DeliveryManRepository;
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
import java.util.Optional;

//TODO Arrumar o Asserts Null do service

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class)
@ActiveProfiles("test")
public class DeliveryManServiceTest {

    @InjectMocks
    private DeliveryManService service;

    @Mock
    private DeliveryManRepository repository;

    private final Integer RESTAURANT_ID = 1;

    @Test
    public void retrieveFirstFreeDeliveryMan() {
        BDDMockito.given(repository.findTop1ByRestaurantIdAndOccupation(RESTAURANT_ID, Occupation.FREE)).willReturn(givenDeliveryMan());
        DeliveryMan deliveryMan = service.retrieveFirstFreeDeliveryMan(RESTAURANT_ID);
        Assert.assertNotNull(deliveryMan);
    }

    @Test
    public void retrieveByRestaurantAndWaitingAcceptance() {
        BDDMockito.given(repository.findByRestaurantIdAndOccupation(RESTAURANT_ID, Occupation.FREE)).willReturn(givenDeliveryMen());
        List<DeliveryMan> deliveryMen = service.retrieveByRestaurantAndWaitingAcceptance(RESTAURANT_ID);
        Assert.assertNotNull(deliveryMen);
    }

    @Test
    public void acceptJob() {
        DeliveryMan deliveryMan = givenDeliveryMan();
        BDDMockito.given(repository.findById(RESTAURANT_ID)).willReturn(Optional.of(deliveryMan));
        BDDMockito.given(repository.save(deliveryMan)).willReturn(deliveryMan);
        DeliveryMan busyDeliveryMan = service.acceptJob(RESTAURANT_ID);
        Assert.assertEquals(busyDeliveryMan.getOccupation(), Occupation.IN_WORK);
    }

    @Test
    public void retrieveById() {
        BDDMockito.given(repository.findById(RESTAURANT_ID)).willReturn(Optional.of(givenDeliveryMan()));
        DeliveryMan deliveryMan = service.retrieveById(RESTAURANT_ID);
        Assert.assertNotNull(deliveryMan);
    }

    private DeliveryMan givenDeliveryMan() {
        DeliveryMan deliveryMan = new DeliveryMan();
        deliveryMan.setOccupation(Occupation.FREE);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(RESTAURANT_ID);
        deliveryMan.setRestaurant(restaurant);
        return deliveryMan;
    }

    private List<DeliveryMan> givenDeliveryMen() {
        DeliveryMan firstDeliveryMan = new DeliveryMan();
        firstDeliveryMan.setOccupation(Occupation.FREE);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(RESTAURANT_ID);
        firstDeliveryMan.setRestaurant(restaurant);
        DeliveryMan secondDeliveryMan = new DeliveryMan();
        secondDeliveryMan.setOccupation(Occupation.FREE);
        secondDeliveryMan.setRestaurant(restaurant);
        return Arrays.asList(firstDeliveryMan, secondDeliveryMan);
    }

}
