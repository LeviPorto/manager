package com.levi.manager.dtos;

import com.levi.manager.entities.enuns.RestaurantCategory;
import lombok.Data;

@Data
public class RestaurantFilteredDTO {

    public RestaurantCategory restaurantCategory;
    public Integer restaurantId;
    private Boolean isIFoodDelivery;
    private Boolean isSuperRestaurant;
    private Boolean hasTrackedDelivery;

}
