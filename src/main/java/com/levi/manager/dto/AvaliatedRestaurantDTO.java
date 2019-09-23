package com.levi.manager.dto;

import lombok.Data;

@Data
public class AvaliatedRestaurantDTO {

    private Integer restaurantId;
    private Double rating;
    private boolean isSuperRestaurant;

}
