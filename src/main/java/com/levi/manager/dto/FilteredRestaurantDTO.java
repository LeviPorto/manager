package com.levi.manager.dto;

import com.levi.manager.domain.Combo;
import com.levi.manager.domain.Food;
import com.levi.manager.domain.Promotion;
import com.levi.manager.domain.enumeration.RestaurantCategory;
import com.levi.manager.domain.parent.LocalizedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FilteredRestaurantDTO that = (FilteredRestaurantDTO) o;
        return restaurantCategory == that.restaurantCategory &&
                Objects.equals(restaurantId, that.restaurantId) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), restaurantCategory, restaurantId, latitude, longitude);
    }
}
