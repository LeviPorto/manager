package com.levi.manager.entities.parents;

import lombok.Data;

import javax.persistence.Column;

@Data
public abstract class PersonalContact extends Contact {

    @Column
    private String CPF;

}
