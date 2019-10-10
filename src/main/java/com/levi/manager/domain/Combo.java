package com.levi.manager.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.levi.manager.crud.IdentifiedEntity;
import com.levi.manager.domain.parent.GenericFood;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

//TODO Fazer o AbstractDao e Replicar essa lógica pr geral

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Combo extends GenericFood implements Serializable, IdentifiedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "combo")
    @JsonBackReference("items")
    private List<ComboItem> items;

    @ManyToOne(targetEntity = Restaurant.class)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @OneToMany(mappedBy = "combo")
    @JsonBackReference("eatingTags")
    private List<EatingTag> eatingTags;


}
