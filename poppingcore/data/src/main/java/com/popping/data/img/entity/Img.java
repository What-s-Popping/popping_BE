package com.popping.data.img.entity;

import com.popping.data.pop.entity.Pop;
import com.popping.data.pop.entity.RePop;
import com.popping.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Img extends BaseEntity {

    @Column(name = "imgName")
    private String imgName;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Pop pop;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private RePop rePop;

    @Builder
    public Img(String imgName, Pop pop, RePop rePop) {
        this.imgName = imgName;
        this.pop = pop;
        this.rePop = rePop;
    }
}
