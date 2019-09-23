package com.levi.manager.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Deliveryman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

}
