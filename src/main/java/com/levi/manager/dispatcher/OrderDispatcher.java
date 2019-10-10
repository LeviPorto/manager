package com.levi.manager.dispatcher;

import com.levi.manager.dto.OrderDTO;
import com.levi.manager.listener.OrderCreatedListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDispatcher {

    private final List<OrderCreatedListener> orderCreateListeners;

    @Autowired
    public OrderDispatcher(List<OrderCreatedListener> orderCreateListeners) {
        this.orderCreateListeners = orderCreateListeners;
    }

    public void notifyOrderCreateListeners(OrderDTO order) {
        if (orderCreateListeners != null) {
            for (OrderCreatedListener listener : orderCreateListeners) {
                listener.orderWasCreate(order);
            }
        }
    }

}
