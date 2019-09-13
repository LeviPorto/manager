package com.levi.manager.subscriber;

import com.levi.manager.dispatcher.RatingDispatcher;
import com.levi.manager.dtos.AvaliatedRestaurantDTO;
import com.levi.manager.entities.Restaurant;
import com.levi.manager.services.RestaurantService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

//TODO nome pacotes plural

@Component
public class RatingSubscriber {

    private final RatingDispatcher ratingDispatcher;
    private final RestaurantService restaurantService;

    public RatingSubscriber(final RatingDispatcher ratingDispatcher, final RestaurantService restaurantService) {
        this.ratingDispatcher = ratingDispatcher;
        this.restaurantService = restaurantService;
    }

    @KafkaListener(topics = "RATING_EVENT_SOURCING", groupId = "1234", containerFactory = "kafkaListenerContainerFactory")
    public void processRatingEventSourcing(@Payload AvaliatedRestaurantDTO avaliatedRestaurantDTO) {
        Restaurant restaurant = restaurantService.retrieveById(avaliatedRestaurantDTO.getRestaurantId());
        ratingDispatcher.notifyUpdateRatingListeners(avaliatedRestaurantDTO, restaurant);
    }

}
