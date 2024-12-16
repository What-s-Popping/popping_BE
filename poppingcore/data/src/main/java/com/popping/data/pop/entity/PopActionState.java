package com.popping.data.pop.entity;

import com.popping.data.member.entity.Member;
import com.popping.data.pop.emotion.ActionState;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PopActionState extends BaseActionState {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pop", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Pop pop;

    @Builder
    public PopActionState(ActionState actionState, Member member, Pop pop) {
        super(actionState, member);
        this.pop = pop;
    }
}
