package com.levi.manager.entity.parent;

import com.levi.manager.entity.EatingTag;
import com.levi.manager.entity.Restaurant;
import com.levi.manager.entity.enumeration.FoodCategory;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@MappedSuperclass
public abstract class Eating {

    @Column
    private Boolean spotLight;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Double price;

    @Column
    private FoodCategory category;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Restaurant.class)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @OneToMany(mappedBy = "eating")
    private List<EatingTag> eatingTags;


}
