package com.levi.manager.domain;

import com.levi.manager.crud.IdentifiedEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class PromotionItem implements Serializable, IdentifiedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Double quantity;

    @OneToOne
    private Restaurant restaurant;

    @ManyToOne(targetEntity = Promotion.class)
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;

}
