package com.levi.manager.entity;

import com.levi.manager.entity.parent.Eating;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Combo extends Eating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "combo")
    private List<ComboItem> items;

}
