package com.levi.manager.domain;

import com.levi.manager.crud.IdentifiedEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class ComboItem implements Serializable, IdentifiedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Double quantity;

    @OneToOne
    private Restaurant restaurant;

    @ManyToOne(targetEntity = Combo.class)
    @JoinColumn(name = "combo_id", nullable = false)
    private Combo combo;


}
