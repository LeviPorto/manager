package com.levi.manager.domain.parent;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Data
@MappedSuperclass
public abstract class LocalizedEntity {

    @Column
    @NotNull
    private Double latitude;
    @Column
    @NotNull
    private Double longitude;

}
