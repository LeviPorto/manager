package com.levi.manager.subscriber;

import com.levi.manager.dispatcher.OrderDispatcher;
import com.levi.manager.dto.OrderDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrderSubscriber {

    private final OrderDispatcher orderDispatcher;

    public OrderSubscriber(final OrderDispatcher orderDispatcher) {
        this.orderDispatcher = orderDispatcher;
    }

    @KafkaListener(topics = "ORDER_EVENT_SOURCING", groupId = "1234", containerFactory = "orderKafkaListenerContainerFactory")
    public void processOrderEventSourcing(@Payload OrderDTO orderDTO) {
        orderDispatcher.notifyOrderCreateListeners(orderDTO);
    }

}
