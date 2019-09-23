package com.levi.manager.entity;

import com.levi.manager.entity.enumeration.RestaurantCategory;
import com.levi.manager.entity.parent.CompanyContact;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Restaurant extends CompanyContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Enumerated(EnumType.STRING)
    private RestaurantCategory category;

    @Column
    private Boolean isSuperRestaurant;

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

}
