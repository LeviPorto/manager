package com.levi.manager.domain;

import com.levi.manager.crud.IdentifiedEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class GenericFoodTag implements Serializable, IdentifiedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @ManyToOne(targetEntity = Food.class)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @ManyToOne(targetEntity = Promotion.class)
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;

    @ManyToOne(targetEntity = Combo.class)
    @JoinColumn(name = "combo_id", nullable = false)
    private Combo combo;

}
