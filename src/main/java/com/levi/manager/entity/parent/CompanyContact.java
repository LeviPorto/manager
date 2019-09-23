package com.levi.manager.entity.parent;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class CompanyContact extends Contact {

    @Column
    private String CNPJ;

}
