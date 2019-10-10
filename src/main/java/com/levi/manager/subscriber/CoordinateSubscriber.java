package com.levi.manager.subscriber;

import com.levi.manager.dispatcher.CoordinateDispatcher;
import com.levi.manager.dto.CoordinateDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class CoordinateSubscriber {

    private final CoordinateDispatcher coordinateDispatcher;

    public CoordinateSubscriber(final CoordinateDispatcher coordinateDispatcher) {
        this.coordinateDispatcher = coordinateDispatcher;
    }

    @KafkaListener(topics = "COORDINATE_EVENT_SOURCING", groupId = "1234", containerFactory = "coordinateKafkaListenerContainerFactory")
    public void processCoordinateEventSourcing(@Payload CoordinateDTO coordinateDTO) {
        coordinateDispatcher.notifyCoordinateCreateListeners(coordinateDTO);
    }

}
