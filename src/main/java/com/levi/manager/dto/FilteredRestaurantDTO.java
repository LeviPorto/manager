package com.levi.manager.dto;

import com.levi.manager.domain.Combo;
import com.levi.manager.domain.Food;
import com.levi.manager.domain.Promotion;
import com.levi.manager.domain.enumeration.RestaurantCategory;
import com.levi.manager.domain.parent.LocalizedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class FilteredRestaurantDTO extends LocalizedEntity {

    public RestaurantCategory restaurantCategory;
    public Integer restaurantId;
    private boolean isIFoodDelivery;
    private boolean isSuperRestaurant;
    private boolean hasTrackedDelivery;
    private Double distanceFromCustomer;
    private Double deliveryFee;
    private Double deliveryTime;
    private Double cost;
    private Double rating;
    private Double latitude;
    private Double longitude;
    private Double restaurantDeliveryFee;
    private List<Food> foods;
    private List<Combo> combos;
    private List<Promotion> promotions;

}
