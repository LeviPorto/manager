package com.levi.manager.domain;

import com.levi.manager.crud.IdentifiedEntity;
import com.levi.manager.domain.enumeration.Occupation;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class DeliveryMan implements Serializable, IdentifiedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private User user;

    @Column
    @Enumerated(EnumType.STRING)
    private Occupation occupation;

    @ManyToOne(targetEntity = Restaurant.class)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column
    private String phoneIMEI;

    public static boolean hasFreeOccupation(DeliveryMan deliveryMan, Occupation occupation) {
        return occupation == deliveryMan.occupation;
    }

}
