package com.levi.manager.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ComboItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Double quantity;

    @OneToOne(fetch = FetchType.EAGER)
    private Food food;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Combo.class)
    @JoinColumn(name = "combo_id", nullable = false)
    private Combo combo;



}
