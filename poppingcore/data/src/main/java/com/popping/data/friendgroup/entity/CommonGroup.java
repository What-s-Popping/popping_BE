package com.popping.data.friendgroup.entity;

import com.popping.global.entity.BaseEntity;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public abstract class CommonGroup extends BaseEntity {

}
