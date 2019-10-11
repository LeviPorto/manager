package com.levi.manager.domain.parent;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class CompanyContact extends Contact {

    @Column(nullable = false)
    @NotBlank
    private String CNPJ;

}
