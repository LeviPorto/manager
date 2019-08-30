package com.levi.manager.dtos;

import com.levi.manager.entities.Food;
import com.levi.manager.entities.enuns.RestaurantCategory;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantFilteredDTO {

    public RestaurantCategory restaurantCategory;
    public Integer restaurantId;
    private Boolean isIFoodDelivery;
    private Boolean isSuperRestaurant;
    private Boolean hasTrackedDelivery;
    private PaymentAcceptanceDTO PaymentAcceptanceDTO;
    private Double distanceFromCustomer;
    private Double deliveryFee;
    private Double deliveryTime;
    private List<Food> foods;

}
