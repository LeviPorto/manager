package com.levi.manager.domain;

import com.levi.manager.crud.IdentifiedEntity;
import com.levi.manager.domain.parent.PersonalContact;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class User extends PersonalContact implements Serializable, IdentifiedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


}
