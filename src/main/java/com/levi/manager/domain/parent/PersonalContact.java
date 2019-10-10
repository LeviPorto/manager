package com.levi.manager.domain.parent;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class PersonalContact extends Contact {

    @Column
    private String CPF;

}
