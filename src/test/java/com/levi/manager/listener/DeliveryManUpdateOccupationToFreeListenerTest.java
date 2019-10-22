package com.levi.manager.listener;

import com.levi.manager.ManagerApplication;
import com.levi.manager.domain.DeliveryMan;
import com.levi.manager.domain.Restaurant;
import com.levi.manager.domain.enumeration.Occupation;
import com.levi.manager.dto.CoordinateDTO;
import com.levi.manager.service.DeliveryManService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class)
@ActiveProfiles("test")
public class DeliveryManUpdateOccupationToFreeListenerTest {

    @InjectMocks
    private DeliveryManUpdateOccupationToFreeListener listener;

    @Mock
    private DeliveryManService service;

    private final Integer DELIVERY_MAN_ID = 1;

    private final Double COORDINATE_LATITUDE = 13.05;
    private final Double COORDINATE_LONGITUDE = 12.15;

    private final Double RESTAURANT_LATITUDE = 13.05;
    private final Double RESTAURANT_LONGITUDE = 12.15;


    @Test
    public void coordinateWasCreated() {
        CoordinateDTO coordinateDTO = givenCoordinate();
        DeliveryMan deliveryMan = givenDeliveryMan();
        BDDMockito.given(service.retrieveById(coordinateDTO.getDeliveryManId())).willReturn(deliveryMan);
        listener.coordinateWasCreated(coordinateDTO);
        Assert.assertEquals(deliveryMan.getOccupation(), Occupation.FREE);
    }

    private CoordinateDTO givenCoordinate() {
        CoordinateDTO coordinate = new CoordinateDTO();
        coordinate.setDeliveryManId(DELIVERY_MAN_ID);
        coordinate.setLatitude(COORDINATE_LATITUDE);
        coordinate.setLongitude(COORDINATE_LONGITUDE);
        return coordinate;
    }

    private DeliveryMan givenDeliveryMan() {
        DeliveryMan deliveryMan = new DeliveryMan();
        deliveryMan.setOccupation(Occupation.IN_WORK);
        Restaurant restaurant = new Restaurant();
        restaurant.setLatitude(RESTAURANT_LATITUDE);
        restaurant.setLongitude(RESTAURANT_LONGITUDE);
        deliveryMan.setRestaurant(restaurant);
        return deliveryMan;
    }

}
