package com.levi.manager.entities.parents;

import lombok.Data;

import javax.persistence.Column;

@Data
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
    private String address;

}
