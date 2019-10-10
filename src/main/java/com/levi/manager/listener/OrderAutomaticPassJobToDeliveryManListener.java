package com.levi.manager.listener;

import com.levi.manager.domain.DeliveryMan;
import com.levi.manager.domain.enumeration.Occupation;
import com.levi.manager.dto.OrderDTO;
import com.levi.manager.service.DeliveryManService;
import org.springframework.stereotype.Component;

import static com.levi.manager.domain.DeliveryMan.hasFreeOccupation;

@Component
public class OrderAutomaticPassJobToDeliveryManListener implements OrderCreatedListener {

    private final DeliveryManService deliveryManService;

    public OrderAutomaticPassJobToDeliveryManListener(DeliveryManService deliveryManService) {
        this.deliveryManService = deliveryManService;
    }

    @Override
    public void orderWasCreate(OrderDTO orderDTO) {
        DeliveryMan firstFreeDeliveryMan = deliveryManService.retrieveFirstFreeDeliveryMan(orderDTO.getRestaurantId());
        if(hasFreeOccupation(firstFreeDeliveryMan, Occupation.FREE)) {
            firstFreeDeliveryMan.setOccupation(Occupation.WAITING_ACCEPTANCE);
            deliveryManService.update(firstFreeDeliveryMan, firstFreeDeliveryMan.getId());
        }
    }

}
