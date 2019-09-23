package com.levi.manager.entity.parent;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class PersonalContact extends Contact {

    @Column
    private String CPF;

}
