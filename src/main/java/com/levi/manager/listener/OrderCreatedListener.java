package com.levi.manager.listener;

import com.levi.manager.dto.OrderDTO;

public interface OrderCreatedListener {

    void orderWasCreate(OrderDTO orderDTO);

}
