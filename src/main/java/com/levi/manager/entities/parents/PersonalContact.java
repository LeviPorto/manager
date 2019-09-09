package com.levi.manager.entities.parents;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class PersonalContact extends Contact {

    @Column
    private String CPF;

}
