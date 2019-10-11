package com.levi.manager.domain;

import com.levi.manager.crud.IdentifiedEntity;
import com.levi.manager.domain.enumeration.Occupation;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Entity
@Table(indexes = {
        @Index(name = "occupation_restaurant_idx", columnList = "occupation, restaurant_id")})
public class DeliveryMan implements Serializable, IdentifiedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Occupation occupation;

    @ManyToOne(targetEntity = Restaurant.class)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    @NotBlank
    private String phoneIMEI;

    public static boolean hasFreeOccupation(DeliveryMan deliveryMan, Occupation occupation) {
        return occupation == deliveryMan.occupation;
    }

}
