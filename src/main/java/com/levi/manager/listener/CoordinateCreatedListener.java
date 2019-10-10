package com.levi.manager.listener;

import com.levi.manager.dto.CoordinateDTO;

public interface CoordinateCreatedListener {

    void coordinateWasCreated(CoordinateDTO coordinateDTO);

}
