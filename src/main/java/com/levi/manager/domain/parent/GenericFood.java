package com.levi.manager.domain.parent;

import com.levi.manager.domain.enumeration.FoodCategory;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@MappedSuperclass
public abstract class GenericFood {

    @Column
    private Boolean spotLight;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column
    private String description;

    @Column
    @Positive
    @NotNull
    private Double price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FoodCategory category;

}
