package com.levi.manager.entity;

import com.levi.manager.entity.enumeration.RestaurantCategory;
import com.levi.manager.entity.parent.CompanyContact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant extends CompanyContact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Enumerated(EnumType.STRING)
    private RestaurantCategory category;

    @Column
    private boolean isSuperRestaurant;

    @Column
    private boolean isIFoodDelivery;

    @Column
    private boolean hasTrackedDelivery;

    @Column
    private Double cost;

    @Column
    private Double deliveryFee;

    @Column
    private Double rating;

    @OneToMany(mappedBy = "restaurant")
    private List<Food> foods;

    @OneToMany(mappedBy = "restaurant")
    private List<Promotion> promotions;

    @OneToMany(mappedBy = "restaurant")
    private List<Combo> combos;

    public Restaurant(Integer id, String name, RestaurantCategory category, Double cost, Double latitude, Double longitude, Double rating,
                      boolean isSuperRestaurant, boolean isIFoodDelivery, boolean hasTrackedDelivery) {
        this.id = id;
        this.category = category;
        this.cost = cost;
        this.rating = rating;
        this.isSuperRestaurant = isSuperRestaurant;
        this.isIFoodDelivery = isIFoodDelivery;
        this.hasTrackedDelivery = hasTrackedDelivery;
        setName(name);
        setLatitude(latitude);
        setLongitude(longitude);
    }


}
