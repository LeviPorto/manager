package com.levi.manager.dispatcher;

import com.levi.manager.dto.CoordinateDTO;
import com.levi.manager.listener.CoordinateCreatedListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CoordinateDispatcher {

    private final List<CoordinateCreatedListener> coordinateCreatedListeners;

    @Autowired
    public CoordinateDispatcher(List<CoordinateCreatedListener> coordinateCreatedListeners) {
        this.coordinateCreatedListeners = coordinateCreatedListeners;
    }

    public void notifyCoordinateCreateListeners(CoordinateDTO coordinate) {
        if (coordinateCreatedListeners != null) {
            for (CoordinateCreatedListener listener : coordinateCreatedListeners) {
                listener.coordinateWasCreated(coordinate);
            }
        }
    }

}
