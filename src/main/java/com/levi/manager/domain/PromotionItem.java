package com.levi.manager.domain;

import com.levi.manager.crud.IdentifiedEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
@Entity
public class PromotionItem implements Serializable, IdentifiedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Positive
    @NotNull
    private Double quantity;

    @ManyToOne(targetEntity = Food.class)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @ManyToOne(targetEntity = Restaurant.class)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(targetEntity = Promotion.class)
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;

}
