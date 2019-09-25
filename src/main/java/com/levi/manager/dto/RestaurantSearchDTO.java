package com.levi.manager.dto;

import com.levi.manager.dto.enumeration.SortSearch;
import com.levi.manager.entity.enumeration.RestaurantCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantSearchDTO {

    private Integer deliveryFee;
    private Integer deliveryTime;
    private List<RestaurantCategory> categories;
    private boolean isIFoodDelivery;
    private boolean isSuperRestaurant;
    private boolean hasTrackedDelivery;
    private PaymentAcceptanceDTO PaymentAcceptanceDTO;
    private SortSearch sortSearch;
    private Integer userId;
    private String userCity;
    private String searchedName;

}
