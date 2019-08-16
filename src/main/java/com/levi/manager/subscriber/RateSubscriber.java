package com.levi.manager.subscriber;

import com.levi.manager.dispatcher.RateDispatcher;
import com.levi.manager.dtos.AvaliatedRestaurantDTO;
import com.levi.manager.entities.Restaurant;
import com.levi.manager.services.RestaurantService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

//TODO nome pacotes plural

@Component
public class RateSubscriber {

    private final RateDispatcher rateDispatcher;
    private final RestaurantService restaurantService;

    public RateSubscriber(final RateDispatcher rateDispatcher, final RestaurantService restaurantService) {
        this.rateDispatcher = rateDispatcher;
        this.restaurantService = restaurantService;
    }

    @KafkaListener(topics = "RATE_EVENT_SOURCING", groupId = "1234", containerFactory = "kafkaListenerContainerFactory")
    public void processRateEventSourcing(@Payload AvaliatedRestaurantDTO avaliatedRestaurantDTO) {
        Restaurant restaurant = restaurantService.retrieveById(avaliatedRestaurantDTO.getRestaurantId());
        rateDispatcher.notifyUpdateRateListeners(avaliatedRestaurantDTO, restaurant);
    }

}
