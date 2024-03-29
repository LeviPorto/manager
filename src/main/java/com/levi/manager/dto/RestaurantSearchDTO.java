package com.levi.manager.dto;

import com.levi.manager.dto.enumeration.SortSearch;
import com.levi.manager.domain.enumeration.RestaurantCategory;
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

    private Double deliveryFee;
    private Double deliveryTime;
    private List<RestaurantCategory> categories;
    private boolean isSuperRestaurant;
    private boolean hasTrackedDelivery;
    private boolean isIFoodDelivery;
    private SortSearch sortSearch;
    private Integer userId;
    private String userCity;
    private String searchedName;

}
