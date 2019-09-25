package com.levi.manager.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class PromotionItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Double quantity;

    @OneToOne(fetch = FetchType.EAGER)
    private Food food;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Promotion.class)
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;

}
