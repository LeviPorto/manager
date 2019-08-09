package com.levi.manager.entities.parents;

import lombok.Data;

import javax.persistence.Column;

@Data
public abstract class CompanyContact extends Contact {

    @Column
    private String CNPJ;

}
