package com.levi.manager.domain.parent;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
abstract class Contact extends LocalizedEntity {

    @Column(nullable = false)
    @NotBlank
    private String phone;
    @Column(nullable = false)
    @NotBlank
    private String name;
    @Column(nullable = false)
    @NotBlank
    private String email;
    @Column
    private String simpleName;
    @Column
    private String street;
    @Column
    private String number;
    @Column
    private String complement;
    @Column(nullable = false)
    @NotBlank
    private String city;
    @Column(nullable = false)
    @NotBlank
    private String state;
    @Column(nullable = false)
    @NotBlank
    private String country;

}
