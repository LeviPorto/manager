package com.levi.manager.dtos;

import com.levi.manager.dtos.enuns.SortSearch;
import com.levi.manager.entities.enuns.RestaurantCategory;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantSearchDTO {

    private Integer deliveryFee;
    private Integer deliveryTime;
    private List<RestaurantCategory> categories;
    private Boolean IFoodDelivery;
    private PaymentAcceptanceDTO PaymentAcceptanceDTO;
    private SortSearch sortSearch;
    private Integer userId;
    private String userCity;

}
