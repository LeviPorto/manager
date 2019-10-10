package com.levi.manager.domain.parent;

import com.levi.manager.domain.enumeration.FoodCategory;
import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public abstract class GenericFood {

    @Column
    private Boolean spotLight;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Double price;

    @Column
    private FoodCategory category;

}
