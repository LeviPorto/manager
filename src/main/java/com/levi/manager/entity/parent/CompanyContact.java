package com.levi.manager.entity.parent;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class CompanyContact extends Contact {

    @Column
    private String CNPJ;

}
