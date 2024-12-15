package com.popping.data.pop.entity;

import com.popping.data.member.entity.Member;
import com.popping.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PopRead extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pop", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Pop pop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member reader;

    @Builder
    public PopRead(Pop pop, Member reader) {
        this.pop = pop;
        this.reader = reader;
    }
}
