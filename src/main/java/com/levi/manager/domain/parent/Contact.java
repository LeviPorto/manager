package com.levi.manager.domain.parent;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
abstract class Contact extends LocalizedEntity {

    @Column
    private String phone;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String simpleName;
    @Column
    private String street;
    @Column
    private String number;
    @Column
    private String complement;
    @Column
    private String city;
    @Column
    private String state;
    @Column
    private String country;

}
