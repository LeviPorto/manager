package com.levi.manager.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.levi.manager.crud.IdentifiedEntity;
import com.levi.manager.domain.parent.GenericFood;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Promotion extends GenericFood implements Serializable, IdentifiedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "promotion")
    @JsonBackReference("promotionItems")
    private List<PromotionItem> promotionItems;

    @OneToMany(mappedBy = "promotion")
    @JsonBackReference("eatingTags")
    private List<GenericFoodTag> genericFoodTags;

    @ManyToOne(targetEntity = Restaurant.class)
    private Restaurant restaurant;

}
