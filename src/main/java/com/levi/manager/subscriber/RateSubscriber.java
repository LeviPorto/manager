package com.levi.manager.subscriber;

import com.levi.manager.dispatcher.RateDispatcher;
import com.levi.manager.dtos.AvaliatedRestaurantDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

//TODO nome pacotes plural

@Component
public class RateSubscriber {

    private final RateDispatcher rateDispatcher;

    public RateSubscriber(final RateDispatcher rateDispatcher) {
        this.rateDispatcher = rateDispatcher;
    }

    @KafkaListener(topics = "RATE_EVENT_SOURCING", groupId = "1234", containerFactory = "kafkaListenerContainerFactory")
    public void processRateEventSourcing(@Payload AvaliatedRestaurantDTO avaliatedRestaurantDTO) {
        rateDispatcher.notifyRateValueListeners(avaliatedRestaurantDTO);
        rateDispatcher.notifySuperRestaurantListeners(avaliatedRestaurantDTO);
    }

}
