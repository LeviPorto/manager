package com.levi.manager.entities;

import com.levi.manager.entities.enuns.RestaurantCategory;
import com.levi.manager.entities.parents.CompanyContact;
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
    private Double rate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant")
    private List<Food> foods;

}
