package com.levi.manager.entity;

import com.levi.manager.entity.parent.Eating;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Food extends Eating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    private ComboItem comboItem;

    @OneToOne(fetch = FetchType.EAGER)
    private PromotionItem promotionItem;

}
