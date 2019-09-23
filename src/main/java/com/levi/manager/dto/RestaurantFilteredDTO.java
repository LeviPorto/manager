package com.levi.manager.dto;

import com.levi.manager.entity.Combo;
import com.levi.manager.entity.Food;
import com.levi.manager.entity.Promotion;
import com.levi.manager.entity.enumeration.RestaurantCategory;
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
    private List<Combo> combos;
    private List<Promotion> promotions;

}
