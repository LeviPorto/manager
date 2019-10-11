package com.levi.manager.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.levi.manager.crud.IdentifiedEntity;
import com.levi.manager.domain.enumeration.RestaurantCategory;
import com.levi.manager.domain.parent.CompanyContact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {
        @Index(name = "city_idx", columnList = "city"),
        @Index(name = "name_city_idx", columnList = "name, city")})
public class Restaurant extends CompanyContact implements Serializable, IdentifiedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RestaurantCategory category;

    @Column
    private Boolean isSuperRestaurant;

    @Column(name = "is_ifood_delivery")
    private Boolean isIFoodDelivery;

    @Column
    private Boolean hasTrackedDelivery;

    @Column
    @Min(0)
    @Max(5)
    @NotNull
    private Double cost;

    @Column
    @NotNull
    private Double deliveryFee;

    @Column
    private Double rating;

    @OneToMany(mappedBy = "restaurant")
    @JsonBackReference("foods")
    private List<Food> foods;

    @OneToMany(mappedBy = "restaurant")
    @JsonBackReference("promotions")
    private List<Promotion> promotions;

    @OneToMany(mappedBy = "restaurant")
    @JsonBackReference("combos")
    private List<Combo> combos;

    @OneToMany(mappedBy = "restaurant")
    @JsonBackReference("deliveryMan")
    private List<DeliveryMan> deliveryMan;

}
