package com.levi.manager.entities;

import com.levi.manager.entities.enuns.FoodCategory;
import lombok.Data;

import javax.persistence.*;

@Data
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Boolean spotLight;

    @Column
    private Boolean isCombo;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Double price;

    @Column
    private FoodCategory category;

    @ManyToOne
    private Restaurant restaurant;

}