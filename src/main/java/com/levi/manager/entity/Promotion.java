package com.levi.manager.entity;

import com.levi.manager.entity.parent.Eating;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Data
@Entity
public class Promotion extends Eating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "promotion")
    private List<PromotionItem> promotionItems;

}
