package com.levi.manager.domain.parent;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class LocalizedEntity {

    @Column
    private Double latitude;
    @Column
    private Double longitude;

}
