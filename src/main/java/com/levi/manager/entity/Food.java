package com.levi.manager.entity;

import com.levi.manager.entity.parent.Eating;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Food extends Eating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    private ComboItem comboItem;

    @OneToOne(fetch = FetchType.EAGER)
    private PromotionItem promotionItem;

}
