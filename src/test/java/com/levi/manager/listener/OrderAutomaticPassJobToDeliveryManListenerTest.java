package com.levi.manager.listener;

import com.levi.manager.ManagerApplication;
import com.levi.manager.domain.DeliveryMan;
import com.levi.manager.domain.enumeration.Occupation;
import com.levi.manager.dto.OrderDTO;
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
public class OrderAutomaticPassJobToDeliveryManListenerTest {

    @InjectMocks
    private OrderAutomaticPassJobToDeliveryManListener listener;

    @Mock
    private DeliveryManService service;

    private final Integer ORDER_ID = 1;
    private final Integer DELIVERY_MAN_ID = 1;

    @Test
    public void orderWasCreate() {
        OrderDTO orderDTO = givenOrder();
        DeliveryMan deliveryMan = givenDeliveryMan();
        BDDMockito.given(service.retrieveFirstFreeDeliveryMan(orderDTO.getRestaurantId())).willReturn(deliveryMan);
        listener.orderWasCreate(orderDTO);
        Assert.assertEquals(deliveryMan.getOccupation(), Occupation.WAITING_ACCEPTANCE);
    }

    private OrderDTO givenOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setRestaurantId(ORDER_ID);
        return orderDTO;
    }

    private DeliveryMan givenDeliveryMan() {
        DeliveryMan deliveryMan = new DeliveryMan();
        deliveryMan.setOccupation(Occupation.FREE);
        deliveryMan.setId(DELIVERY_MAN_ID);
        return deliveryMan;
    }

}
