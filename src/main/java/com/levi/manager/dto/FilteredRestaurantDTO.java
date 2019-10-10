package com.levi.manager.dto;

import com.levi.manager.domain.Combo;
import com.levi.manager.domain.Food;
import com.levi.manager.domain.Promotion;
import com.levi.manager.domain.enumeration.RestaurantCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FilteredRestaurantDTO {

    public RestaurantCategory restaurantCategory;
    public Integer restaurantId;
    private boolean isIFoodDelivery;
    private boolean isSuperRestaurant;
    private boolean hasTrackedDelivery;
    private PaymentAcceptanceDTO PaymentAcceptanceDTO;
    private Double distanceFromCustomer;
    private Double deliveryFee;
    private Double deliveryTime;
    private List<Food> foods;
    private List<Combo> combos;
    private List<Promotion> promotions;

}
